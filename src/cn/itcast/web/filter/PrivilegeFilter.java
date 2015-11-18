package cn.itcast.web.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.itcast.domin.User;
/**
 * Ȩ�޹�����
 * @author samsung
 *
 */

public class PrivilegeFilter implements Filter{
	//���list���admin  ��ɫ�û�  ���Է�����Դ·��
	private List<String> adminPathList = new ArrayList<String>();

	//���list���user  ��ɫ�û�  ���Է�����Դ·��
	private List<String> userPathList = new ArrayList<String>();

	@Override
	public void destroy() {
	
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		
		//����ҳ���Ƿ���Ȩ�޷���
		//1������û�����·��
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String visitPath = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
		//2�ж�·���Ƿ���� adminPathList�� userPathList
		if(adminPathList.contains(visitPath)||userPathList.contains(visitPath)){
			//��Ҫ��¼  һ��Ȩ�޲ſ��Է���
			//3,�ж��Ƿ��¼
			User user = (User) httpServletRequest.getSession().getAttribute("loginUser");
			if(user!=null){
				//�Ѿ���¼
				//�ж��û��Ľ�ɫ
				if(user.getRole().equals("admin")){//����Ա
					if(adminPathList.contains(visitPath)){
						chain.doFilter(httpServletRequest, response);
					}else{
						throw new RuntimeException("����Ȩ�޲��㣡��ĵ�ǰȨ���ǣ�"+user.getRole());
					}
					
					
				}else if(user.getRole().equals("user")){//�̳��û�
					if(userPathList.contains(visitPath)){ 		
						chain.doFilter(httpServletRequest, response);
					}else{
						throw new RuntimeException("����Ȩ�޲��㣡��ĵ�ǰȨ���ǣ�"+user.getRole());
					}
					
					
				}
				
			}else{
				//δ��¼
				request.setAttribute("msg", "��û�е�¼���뾡��ȥ��¼");
				request.getRequestDispatcher("/login.jsp").forward(httpServletRequest, response);
			}
		}else{
			//�ο;Ϳ��Է���
			chain.doFilter(httpServletRequest, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//��ȡ admin.txt��user.txt
		try {
			BufferedReader reader1 = new BufferedReader(new FileReader(PrivilegeFilter.class.getResource("/admin.txt").getFile()));
			String line1;
			while((line1=reader1.readLine())!=null){
				adminPathList.add(line1);
			}
			reader1.close();
			BufferedReader reader2 = new BufferedReader(new FileReader(PrivilegeFilter.class.getResource("/user.txt").getFile()));
			String line2;
			while((line2=reader2.readLine())!=null){
				userPathList.add(line2);
			}
			reader2.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
