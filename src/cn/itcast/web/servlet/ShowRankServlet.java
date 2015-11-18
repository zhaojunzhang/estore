package cn.itcast.web.servlet;
/**
 * 查看榜单
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
		//直接调用  业务层  获得榜单 数据
		RankService rankService = new RankService();
		List<Orderitem> rank = rankService.showRank();
		
		//需要将榜单数据  显示Jsp
		//如果  将榜单数据   保存request对象   ， 导出榜单时 需要重新再查询一次        考虑session、applic
		
		getServletContext().setAttribute("rank", rank);
		request.getRequestDispatcher("/rank.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
