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
	
	public Customer getCust() {
		return cust;
	}
	
	public void setCust(Customer cust) {
		this.cust = cust;
	}
	
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
