package com.virtusa.jsm.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@TableGenerator(name = "generate_customer", 
					table = "z_generate_id",	
					pkColumnName = "generate_name", 
					valueColumnName = "generate_value",
					allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE,generator = "generate_customer")
	int customerid;
	String name;
	String email;
	String contact;
	int buildno;
	String streetName;
	String city;
	String pin;
	
//	@OneToMany
//	List<Order> orders=new ArrayList<>();
	
	
//	@JsonIgnore
//	@OneToMany(mappedBy = "cust_id")
//	List<Bill> bills=new ArrayList<>();
	
	
	
	
	public Customer() {
		super();
	}
	
	

@Override
	public String toString() {
		return "Customer [customerid=" + customerid + ", name=" + name + ", email=" + email + ", contact=" + contact
				+ ", buildno=" + buildno + ", streetName=" + streetName + ", city=" + city + ", pin=" + pin
				+  "]";
	}



public Customer(int customerid, String name, String email, String contact, int buildno, String streetName, String city,
		String pin) {
	super();
	this.customerid = customerid;
	this.name = name;
	this.email = email;
	this.contact = contact;
	this.buildno = buildno;
	this.streetName = streetName;
	this.city = city;
	this.pin = pin;
}



//	public Customer(int customerid, String name, String email, String contact, int buildno, String streetName,
//			String city, String pin, String password, List<Bill> bills) {
//		super();
//		this.customerid = customerid;
//		this.name = name;
//		this.email = email;
//		this.contact = contact;
//		this.buildno = buildno;
//		this.streetName = streetName;
//		this.city = city;
//		this.pin = pin;
//		this.password = password;
//		this.bills = bills;
//	}

	public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getBuildno() {
		return buildno;
	}

	public void setBuildno(int buildno) {
		this.buildno = buildno;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

//	public List<Bill> getBills() {
//		return bills;
//	}
//
//	public void setBills(List<Bill> bills) {
//		this.bills = bills;
//	}
//
//	@Override
//	public String toString() {
//		return "Customer [customerid=" + customerid + ", name=" + name + ", email=" + email + ", contact=" + contact
//				+ ", buildno=" + buildno + ", streetName=" + streetName + ", city=" + city + ", pin=" + pin
//				+ ", password=" + password + ", bills=" + bills + "]";
//	}

	
	
}
