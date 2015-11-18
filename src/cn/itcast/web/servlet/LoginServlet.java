package cn.itcast.web.servlet;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;



import cn.itcast.domin.User;
import cn.itcast.service.UserService;
//�û���¼
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//���form����  ��װUser����
		User user = new User();
		
		
		try {
		BeanUtils.populate(user,request.getParameterMap());
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//����  user �����ҵ�����ɵ�¼
		UserService service = new UserService();
		User loginUser = service.login(user);
		System.out.println(loginUser);
		//�ж�loginUser�Ƿ����
		if(loginUser==null){
			//��¼ʧ��
		  request.setAttribute("msg", "�û��������������");
		  request.getRequestDispatcher("/login.jsp").forward(request, response);
		  
		  
		}else{
			//��½�ɹ�
			request.getSession().setAttribute("loginUser", loginUser);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
