package com.virtusa.jsm.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "shop")
public class Shop {
	
	@Id
	int shopid;
	String email;
	String contact;
	int buildno;
	String streetName;
	String city;
	String pin;
	String password;
	
//	@OneToMany(mappedBy = "shop_shopid")
//	List<Product> products=new ArrayList<>();
	
//	@OneToMany(mappedBy = "shop_shopid")
//	List<Bill> bills=new ArrayList<>();

//	public Shop(int shopid, String userName, String email, String contact, int buildno, String streetName, String city,
//			String pin, String password, List<Product> products/*, List<Bill> bills*/) {
//		super();
//		this.shopid = shopid;
//		this.userName = userName;
//		this.email = email;
//		this.contact = contact;
//		this.buildno = buildno;
//		this.streetName = streetName;
//		this.city = city;
//		this.pin = pin;
//		this.password = password;
//		this.products = products;
//		this.bills = bills;
//	}

	public Shop() {
		super();
	}

	

	public Shop(int shopid, String email, String contact, int buildno, String streetName, String city, String pin,
		String password) {
	super();
	this.shopid = shopid;
	this.email = email;
	this.contact = contact;
	this.buildno = buildno;
	this.streetName = streetName;
	this.city = city;
	this.pin = pin;
	this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getShopid() {
		return shopid;
	}

	public void setShopid(int shopid) {
		this.shopid = shopid;
	}

	@Override
	public String toString() {
		return "Shop [shopid=" + shopid + ", email=" + email + ", contact=" + contact + ", buildno=" + buildno
				+ ", streetName=" + streetName + ", city=" + city + ", pin=" + pin + ", password=" + password + "]";
	}

	

//	public List<Product> getProducts() {
//		return products;
//	}
//
//	public voshopid setProducts(List<Product> products) {
//		this.products = products;
//	}

//	public List<Bill> getBills() {
//		return bills;
//	}
//
//	public voshopid setBills(List<Bill> bills) {
//		this.bills = bills;
//	}

//	@Overrshopide
//	public String toString() {
//		return "Shop [shopid=" + shopid + ", userName=" + userName + ", email=" + email + ", contact=" + contact + ", buildno="
//				+ buildno + ", streetName=" + streetName + ", city=" + city + ", pin=" + pin + ", password=" + password
//				+ ", products=" + products /*+ ", bills=" + bills */+ "]";
//	}
//	
	
	

	
}
