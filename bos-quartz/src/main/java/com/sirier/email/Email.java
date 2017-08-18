package com.sirier.email;

/**
 * 封装邮件实体类
 */
public class Email {

    /**
     * 发件人
     **/
    private String fromAddress;

    /**
     * 收件人
     **/
    private String[] toAddress;

    /**
     * 邮件主题/标题
     **/
    private String subject;

    /**
     * 邮件内容
     **/
    private String content;

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String[] getToAddress() {
        return toAddress;
    }

    public void setToAddress(String[] toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
