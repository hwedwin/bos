package com.sirier.email;

import com.sirier.domain.WorkBill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import freemarker.template.Configuration;
import freemarker.template.Template;
import sun.misc.BASE64Encoder;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    @Qualifier("freeMarkerConfigurer")
    private Cloneable freeMarkerConfigurer;

    /**
     * 不传入外部list数据和图片的邮件发送
     * @param email
     */
    @Override
    public void sendMail(final Email email) {

        final Map<String,Object> map = new HashMap<String,Object>(1);
        map.put("fromAddressMail", email.getFromAddress());
        map.put("content", email.getContent());

        sendWithMapSyncOrNot(email, map);
    }

    /**
     * 带list数据的邮件发送
     * @param email
     * @param list
     */
    @Override
    public void sendMail(Email email, List<WorkBill> list) {

        //根据模版去封装map的key和value
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("workbills", list);
        map.put("fromAddressMail", email.getFromAddress());

        sendWithMapSyncOrNot(email, map);
    }


    /**
     * 带图片+数据的邮件发送
     * @param email
     * @param list
     * @param imgurl
     */
    @Override
    public void sendMail(Email email, List<WorkBill> list, String imgurl) {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("workbills", list);
        //存放图片到map中
        map.put("image", getImageStr(imgurl));
        map.put("fromAddressMail", email.getFromAddress());

        sendWithMapSyncOrNot(email, map);

    }

    /**
     * 根据发送邮件数量来判定是异步还是同步发送邮件
     * @param email
     * @param map
     */
    public void sendWithMapSyncOrNot(final Email email, final Map<String,Object> map) {
        if (email.getToAddress().length > 2) {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        sendWithMap(email, map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            //否则直接发送
            sendWithMap(email, map);
        }
    }


    /**
     * 抽取出来的使用模版发送邮件的方法
     * @param email email实体对象
     * @param map   封装了与模版对应的key-value的map集合
     */
    public void sendWithMap(Email email, Map<String,Object> map) {

        try {
            String text = "";
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");

            Configuration con = (Configuration)freeMarkerConfigurer;
            Template template = con.getTemplate("workbill.ftl");
            text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

            helper.setFrom(email.getFromAddress());// 发件人
            helper.setTo(email.getToAddress());// 收件人
            helper.setReplyTo(email.getFromAddress());// 回复到
            helper.setSubject(email.getSubject());// 邮件主题
            helper.setText(text, true);// true表示设定html格式

            mailSender.send(mime);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取图片的二进制字符串数据
     * @param imgurl 图片的磁盘路径
     * @return
     */
    private String getImageStr(String imgurl) {
        String imgFile = imgurl;
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将邮件中的图片从磁盘中加载,通过字节流 读取编码 将图片转换成对应的字符串数据
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }


}
