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
		//��������  �����Ʒ���
		String id = request.getParameter("id");
		//����ҵ���  �����Ʒ����
		ProductService productservice = new ProductService();
		Product product = productservice.showproduct(id);
		
		//���Session�еĹ��ﳵ
		Map<Product,Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
		//���ﳵ��һ��ʹ��
		if(cart==null){
			//�´������ﳵ
			cart=new HashMap<Product,Integer>();
			
		}
		//�жϹ��ﳵ�����Ʒ
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
