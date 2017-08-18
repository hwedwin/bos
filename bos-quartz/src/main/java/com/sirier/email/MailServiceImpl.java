package com.sirier.email;

import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class MailServiceImpl implements MailService {

    // 注入mailSender,taskExecutor,freeMarkerConfigurer-->set方法注入
    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private TaskExecutor taskExecutor;// 注入Spring封装的异步执行器

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    private Cloneable freeMarkerConfigurer;

    public void setFreeMarkerConfigurer(Cloneable freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Override
    public void sendMail(Email email) {
        if (email.getToAddress() == null || email.getToAddress().length == 0) {
            System.out.println("------没有收件人----------");
            return;
        }

        // 收件人大于2封时，采用异步发送
        if (email.getToAddress().length > 2) {
            sendMailByAsynchronousMode(email);
            System.out.println("------异步发送----------");
        }
        else {
            sendMailBySynchronizationMode(email);
            System.out.println("------同步发送----------");
        }
    }

    @Override // 采用线程池 异步发送邮件,.,,,
    //@Async // 表示该方式是异步...-->可以使用注解
    public void sendMailByAsynchronousMode(final Email email) {
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    System.out.println("------异步发送-----ing-----");
                    sendMailBySynchronizationMode(email);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 发送邮件，采用freemarker模板引擎完成邮件格式封装
     */
    @Override
    public void sendMailBySynchronizationMode(Email email) {
        try {
            String text = "";
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");

            //把需要放置模版的数据封装,然后填充给configuration
            Map<String,String> map = new HashMap<String,String>(1);
            map.put("fromAddressMail", email.getFromAddress());
            map.put("content", email.getContent());
            //${fromAddressMail}
            //  <td>${workbill_id}</td>
            // <td>${workbill_state}</td>
            // <td>${workbill_num}</td>
            // <td>${workbill_staff}</td>

            try {
                //根据模板内容，动态把map中的数据填充进去，生成HTML
                Configuration con = (Configuration)freeMarkerConfigurer;
                Template template = con.getTemplate("workbill.ftl");// 默认加载路径src下

                //把上面封装的map数据传递给模版,拿着返回值text去发送
                //  text = FreeMarkerTemplateUtils.processTemplateIntoString(template,map);

                text = email.getContent();
            } catch (Exception e) {
                e.printStackTrace();
            }

            helper.setFrom(email.getFromAddress());// 发件人
            helper.setTo(email.getToAddress());// 收件人
            helper.setReplyTo(email.getFromAddress());// 回复到
            helper.setSubject(email.getSubject());// 邮件主题
            helper.setText(text, true);// true表示设定html格式

            mailSender.send(mime);
        } catch (MailException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
