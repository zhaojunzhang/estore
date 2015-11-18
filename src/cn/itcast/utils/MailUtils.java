package cn.itcast.utils;

import java.util.Properties;

import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.itcast.domin.User;

/**
 * �����ʼ� ���߷���
 * 
 * @author seawind
 * 
 */
public class MailUtils {
	// ���ɼ�����
	public static String generateActivecode() {
		return UUID.randomUUID().toString();
	}
 
	// �����ʼ�
	public static void sendMail(Message message, Session session)
			throws Exception {
		Transport transport = session.getTransport();
		transport.connect("service", "111");
		transport.sendMessage(message, message.getAllRecipients());
	}

	// �����ʼ�
	public static Message generateMessage(Session session, User user)
			throws Exception {
		MimeMessage message = new MimeMessage(session);
		// �ʼ�ͷ
		message.setFrom(new InternetAddress("service@estore.com"));// ������
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(user
				.getEmail())); // �ռ���
		message.setSubject("ESTORE�̳� �����ʼ�");
		// �ʼ�ͷ
		message
				.setContent(
						"<h2>��ӭ"
								+ user.getUsername()
								+ "ע��Estore�̳ǣ�������Թ�������Ҫ��Ʒ��</h2><h3>����2Сʱ�ڵ��������������˻����</h3><a href='http://www.estore.com/active?activecode="
								+ user.getActivecode()
								+ "'>http://www.estore.com/active?activecode="
								+ user.getActivecode() + "</a>",
						"text/html;charset=utf-8");
		return message;
	}

	// �����Ự
	public static Session createSession() {
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.host", "localhost");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties);
		return session;
	}
}
