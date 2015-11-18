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
 * 用户注册
 * @author samsung
 *
 */

public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1，判断验证码是否正确
		//获得客户端验证码
		String checkcode = request.getParameter("checkcode");
		//获得sesson 验证码
		String checkcode_session=(String) request.getSession().getAttribute("checkcode_session");
		request.getSession().removeAttribute("checkcode_session");
		if(checkcode==null||!checkcode.equals(checkcode_session)){
			//验证码错误
			request.setAttribute("msg", "验证码输入错误");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
		}else{
			//验证码正确
			//2 封装for数据到  javaBean
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
		//3,传递javabean给业务层
			UserService service = new UserService();
			service.regist(user);
			
			//4.显示注册信息
			response.getWriter().print("注册成功！激活邮件已发送您注册邮箱，请于2小时内完成账号激活！");
		}
		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
