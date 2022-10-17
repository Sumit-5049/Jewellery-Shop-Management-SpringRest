package com.virtusa.jsm.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="qorder")
public class QOrder {

	@Id
	@TableGenerator(name = "generate_order", 
					table = "z_generate_id",	
					pkColumnName = "generate_name", 
					valueColumnName = "generate_value",
					allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE,generator = "generate_order")
	int orderid;
	@OneToMany(cascade = CascadeType.ALL)
	List<Cart> carts=new ArrayList<>();
	
//	@Column(name="nettax")
	Double tax;
//	@Column(name="net")
	Double total;
	public QOrder(int orderid, List<Cart> carts, Double tax, Double total) {
		super();
		this.orderid = orderid;
		this.carts = carts;
		this.tax = tax;
		this.total = total;
	}
	public QOrder() {
		super();
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public List<Cart> getCarts() {
		return carts;
	}
	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "Order [orderid=" + orderid + ", carts=" + carts + ", tax=" + tax + ", total=" + total + "]";
	}
	
	
}
