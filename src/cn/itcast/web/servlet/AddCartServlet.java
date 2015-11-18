package cn.itcast.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domin.Product;
import cn.itcast.service.ProductService;

public class AddCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从请求中  获得商品编号
		String id = request.getParameter("id");
		//调用业务层  获得商品对象
		ProductService productservice = new ProductService();
		Product product = productservice.showproduct(id);
		
		//获得Session中的购物车
		Map<Product,Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
		//购物车第一次使用
		if(cart==null){
			//新创建购物车
			cart=new HashMap<Product,Integer>();
			
		}
		//判断购物车里的物品
		if(cart.containsKey(product)){
			int number = cart.get(product);
			cart.put(product, number+1);
		}else{
			cart.put(product, 1);
		}
		
		request.getSession().setAttribute("cart", cart);
		request.getRequestDispatcher("/listproduct").forward(request, response);
		
		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
