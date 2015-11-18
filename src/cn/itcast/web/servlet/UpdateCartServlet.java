package cn.itcast.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domin.Product;

public class UpdateCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id=request.getParameter("id");
		int number = Integer.parseInt(request.getParameter("number"));
		Product product= new Product();
		product.setId(id);
		Map<Product,Integer> cart= (Map<Product, Integer>) request.getSession().getAttribute("cart");
		
		if(number<=0){
			//É¾³ý¸ÃÉÌÆ·
			cart.remove(product);
		}else{
			cart.put(product, number);
		}
		request.getSession().setAttribute("cart", cart);
		request.getRequestDispatcher("/cart.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
