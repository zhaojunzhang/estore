package cn.itcast.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domin.Product;

public class DelCartItemServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获得请求中的参数
		String id =request.getParameter("id");
		//构造商品对象
		Product product = new Product();
		product.setId(id);
		Map<Product,Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
		cart.remove(product);//重写hashcode 和  equals方法
		
		request.getRequestDispatcher("/cart.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
