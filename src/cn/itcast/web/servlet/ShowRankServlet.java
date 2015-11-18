package cn.itcast.web.servlet;
/**
 * �鿴��
 */

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domin.Orderitem;
import cn.itcast.service.RankService;

public class ShowRankServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ֱ�ӵ���  ҵ���  ��ð� ����
		RankService rankService = new RankService();
		List<Orderitem> rank = rankService.showRank();
		
		//��Ҫ��������  ��ʾJsp
		//���  ��������   ����request����   �� ������ʱ ��Ҫ�����ٲ�ѯһ��        ����session��applic
		
		getServletContext().setAttribute("rank", rank);
		request.getRequestDispatcher("/rank.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
