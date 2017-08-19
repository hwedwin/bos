package com.sirier.email;

import com.sirier.domain.WorkBill;

import java.util.List;

public interface MailService {

    /**
     * 纯文字的邮件发送
     * @param email
     */
    public void sendMail(Email email) throws Exception;

    /**
     * 带数据的邮件发送
     * @param email
     * @param list
     */
    public void sendMail(Email email, List<WorkBill> list) throws Exception;


    /**
     * 带图片和数据的邮件发送
     * @param email
     * @param list
     * @param imgurl
     */
    public void sendMail(Email email, List<WorkBill> list, String imgurl) throws Exception;

}
