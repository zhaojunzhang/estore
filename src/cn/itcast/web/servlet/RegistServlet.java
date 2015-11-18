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
/**
 * �û�ע��
 * @author samsung
 *
 */

public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1���ж���֤���Ƿ���ȷ
		//��ÿͻ�����֤��
		String checkcode = request.getParameter("checkcode");
		//���sesson ��֤��
		String checkcode_session=(String) request.getSession().getAttribute("checkcode_session");
		request.getSession().removeAttribute("checkcode_session");
		if(checkcode==null||!checkcode.equals(checkcode_session)){
			//��֤�����
			request.setAttribute("msg", "��֤���������");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
		}else{
			//��֤����ȷ
			//2 ��װfor���ݵ�  javaBean
			User user = new User();
			
			try {
				BeanUtils.populate(user, request.getParameterMap());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//3,����javabean��ҵ���
			UserService service = new UserService();
			service.regist(user);
			
			//4.��ʾע����Ϣ
			response.getWriter().print("ע��ɹ��������ʼ��ѷ�����ע�����䣬����2Сʱ������˺ż��");
		}
		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
