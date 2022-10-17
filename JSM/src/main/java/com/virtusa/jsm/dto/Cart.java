package com.virtusa.jsm.dto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "Cart")
public class Cart {
	
	@Id
	@TableGenerator(name = "generate_cart", 
					table = "z_generate_id",	
					pkColumnName = "generate_name", 
					valueColumnName = "generate_value",
					allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE,generator = "generate_cart")
	int cartid;
	@OneToOne(cascade = CascadeType.ALL)
	Product p;
	int purchesquantity;	
	double price; 	//priceperone*quantity
	double rateper10gm;
	
	
	public Cart(int cartid, Product p, int purchesquantity, double price, double rateper10gm) {
		super();
		this.cartid = cartid;
		this.p = p;
		this.purchesquantity = purchesquantity;
		this.price = price;
		this.rateper10gm = rateper10gm;
	}
	public Cart() {
		super();
	}
	public Product getP() {
		return p;
	}
	public void setP(Product p) {
		this.p = p;
	}
	public int getPurchesquantity() {
		return purchesquantity;
	}
	public void setPurchesquantity(int purchesquantity) {
		this.purchesquantity = purchesquantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getCartid() {
		return cartid;
	}
	public void setCartid(int cartid) {
		this.cartid = cartid;
	}
	public double getRateper10gm() {
		return rateper10gm;
	}
	public void setRateper10gm(double rateper10gm) {
		this.rateper10gm = rateper10gm;
	}
	@Override
	public String toString() {
		return "Cart [cartid=" + cartid + ", p=" + p + ", purchesquantity=" + purchesquantity + ", price=" + price
				+ ", rateper10gm=" + rateper10gm + "]";
	}
	
	
	
}
