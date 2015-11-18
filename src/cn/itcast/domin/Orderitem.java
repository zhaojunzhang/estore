package cn.itcast.domin;

public class Orderitem {
	private String order_id;
	private String product_id;
	private int buynum;
    private double money;
    
    //在订单显示商品信息
    private Product product;
    
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String orderId) {
		order_id = orderId;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String priductId) {
		product_id = priductId;
	}
	public int getBuynum() {
		return buynum;
	}
	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	

}
