package cn.itcast.service;

import java.util.List;

import cn.itcast.dao.ProductDAO;
import cn.itcast.domin.Product;

public class ProductService {
	
public void addproduct(Product product){
	ProductDAO  productDAO= new ProductDAO();
	productDAO.insert(product);
}

public List<Product> listproduct() {
	ProductDAO productDAO=new ProductDAO();
	
	return productDAO.findAll(); 
}

public Product showproduct(String id) {
	ProductDAO productDAO =new ProductDAO();
	
	return productDAO.findById(id);
}
}
