package com.sirier.quartz;

import com.sirier.dao.IWorkBillDao;
import com.sirier.domain.WorkBill;
import com.sirier.email.Email;
import com.sirier.email.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 发送邮件的工具类,已配置到quartz管理执行
 * Created by Sirierx on 2017/8/17.
 */
@Component("MyJobs")
public class MyJobs {

    @Autowired
    private IWorkBillDao workBillDao;// 工单 订单自动分单 成功 生成一张工单
    @Autowired
    private MailService mailService;// 注入邮件服务类 sendMail(Email) 发送邮件


    private void mySendEmailJob() {
        //调用执行发送邮件
        System.out.println("---发邮件啦---");

        List<WorkBill> list = workBillDao.findAll();

        StringBuilder sb = new StringBuilder();

        for (WorkBill bill : list) {
            sb.append("工单编号:" + bill.getId() +
                    "--取派员信息:" + bill.getStaff().getName() +
                    "--工单状态:" + bill.getType() +
                    "--工单追单次数:" + bill.getAttachbilltimes() + "\r\n");
        }
        Email email = new Email();
        email.setContent(sb.toString());
        email.setFromAddress("137520767@qq.com");
        email.setSubject("工单汇总");
        email.setToAddress(new String[]{ "sirierx@foxmail.com" });// 发送多人 添加即可
        mailService.sendMail(email);
        System.out.println("email ----发送-workbill---");
    }

}
