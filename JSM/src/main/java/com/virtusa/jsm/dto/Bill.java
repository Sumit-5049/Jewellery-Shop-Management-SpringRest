package com.virtusa.jsm.dto;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "bill")
public class Bill {

	@Id
	@TableGenerator(name = "generate_bill", 
					table = "z_generate_id",	
					pkColumnName = "generate_name", 
					valueColumnName = "generate_value",
					allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE,generator = "generate_bill")
	int billid;
	Timestamp dateTime;
	@OneToOne
	QOrder ord;
	@OneToOne
	Customer cust;
	
//	@OneToMany
//	List<QOrder> oredrs=new ArrayList<>();
//
//	@Column(name="nettax")
//	Double tax;
//	@Column(name="net")
//	Double total;
	public Bill() {
		super();
	}
	
	
	
	public Bill(int billid, Timestamp dateTime, QOrder o, Customer cust) {
		super();
		this.billid = billid;
		this.dateTime = dateTime;
		this.ord = o;
		this.cust = cust;
	}



	public int getBillid() {
		return billid;
	}
	public void setBillid(int billid) {
		this.billid = billid;
	}
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
//	public List<QOrder> getOredrs() {
//		return oredrs;
//	}
//	public void setOredrs(List<QOrder> oredrs) {
//		this.oredrs = oredrs;
//	}
//	public Double getTax() {
//		return tax;
//	}
//	public void setTax(Double tax) {
//		this.tax = tax;
//	}
//	public Double getTotal() {
//		return total;
//	}
//	public void setTotal(Double total) {
//		this.total = total;
//	}
	public Customer getCust() {
		return cust;
	}
	public void setCust(Customer cust) {
		this.cust = cust;
	}
//	public int getShop_id() {
//		return shop_id;
//	}
//	public void setShop_id(int shop_id) {
//		this.shop_id = shop_id;
//	}
//	public Bill(int billid, Timestamp dateTime, List<QOrder> oredrs, Double tax, Double total, int cust,
//			int shop_id) {
//		super();
//		this.billid = billid;
//		this.dateTime = dateTime;
//		this.oredrs = oredrs;
//		this.tax = tax;
//		this.total = total;
//		this.cust = cust;
//		this.shop_id = shop_id;
//	}
//	@Override
//	public String toString() {
//		return "Bill [billid=" + billid + ", dateTime=" + dateTime + ", oredrs=" + oredrs + ", tax=" + tax + ", total="
//				+ total + ", cust=" + cust + ", shop_id=" + shop_id + "]";
//	}
	public QOrder getord() {
		return ord;
	}
	public void setord(QOrder o) {
		this.ord = o;
	}
	@Override
	public String toString() {
		return "Bill [billid=" + billid + ", dateTime=" + dateTime + ", ord=" + ord + ", cust=" + cust +"]";
	}
	
	
	
}
