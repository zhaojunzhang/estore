package cn.itcast.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.service.UserService;
/**
 * �����˻�
 * @author samsung
 *
 */
public class ActiveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String activecode= request.getParameter("activecode");
		
		
		//�������˻����ݸ�ҵ���  ����˻��ļ���
		UserService service= new UserService();
		service.active(activecode);
		
		//֪ͨ�û� �˻�����ɹ�
		response.getWriter().println("�˻�����ɹ�");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
