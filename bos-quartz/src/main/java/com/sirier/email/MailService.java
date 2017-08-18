package com.sirier.email;

public interface MailService {
	public void sendMail(Email email);

	// 采用异步发送邮件
	public void sendMailByAsynchronousMode(Email email);

	// 同步发送邮件
	public void sendMailBySynchronizationMode(Email email);

}
