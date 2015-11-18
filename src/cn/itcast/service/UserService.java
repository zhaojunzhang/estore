package cn.itcast.service;

import javax.mail.Message;
import javax.mail.Session;

import cn.itcast.dao.UserDAO;
import cn.itcast.domin.User;
import cn.itcast.utils.MailUtils;

public class UserService {
	
	
//�û�ע��
	//����ûд����ֵ���û���  ���� �����ظ�����
	  public void regist(User user){
		  //1,���û���Ϣ  �������ݿ�
		  //���ɼ�����
		  String activecode = MailUtils.generateActivecode();
		  user.setActivecode(activecode);
		  
		  //���ݸ�DAO
		  UserDAO dao = new UserDAO();
		  dao.insert(user);
		  //2,���ͼ����ʼ�-----
		  
		  //����Session
		  Session session = MailUtils.createSession();
		  
		  //��д�ʼ�
		  try {
			Message message = MailUtils.generateMessage(session, user);
			//�����ʼ�
			MailUtils.sendMail(message, session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("���ͼ����ʼ�ʧ�ܣ�");
		}  
	  }
	  //�˻�����
	  public void active(String activecode){
		  //1,���ݼ�����   ��ѯ�˻�----�жϼ��������ô
		  UserDAO dao= new UserDAO();
		  User user = dao.findByActivecode(activecode);
		  if(user==null){
			  //��������Ч
			  throw new RuntimeException("��������Ч");
			  
		  }else{
			  //���������
			  //2���жϼ������Ƿ��Ѿ�����
			  if(System.currentTimeMillis()-user.getRegisttime().getTime()>1000*60*60*2){
				  //������Сʱ
				  //TODO  ���·���---�������ݿⱣ�漤����
				  throw new RuntimeException("�������Ѿ�����");
				  
			  }else{
				  //3���Լ���
				  user.setState(1);
				  dao.updateState(user);
			  }
			  
		  }
	  }
	  //�û���¼---���ذ��������û���Ϣ����
	  public User login(User user){
		  //�����û���  ������  ��DAO
		  UserDAO useDAO = new UserDAO();
		
		return useDAO.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		  
	  }
}
