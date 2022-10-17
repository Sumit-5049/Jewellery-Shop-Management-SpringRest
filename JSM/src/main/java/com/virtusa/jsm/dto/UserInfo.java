package com.virtusa.jsm.dto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

@Entity
public class UserInfo {
	@Id
	@TableGenerator(name = "generate_user", 
					table = "z_generate_id",	
					pkColumnName = "generate_name", 
					valueColumnName = "generate_value",
					allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE,generator = "generate_user")
	int userInfoid;
	@OneToOne(cascade = CascadeType.ALL)
	Customer c;
	String password;
	public UserInfo(int userInfoid, Customer c, String password) {
		super();
		this.userInfoid = userInfoid;
		this.c = c;
		this.password = password;
	}
	public UserInfo() {
		super();
	}
	public int getUserInfoid() {
		return userInfoid;
	}
	public void setUserInfoid(int userInfoid) {
		this.userInfoid = userInfoid;
	}
	public Customer getC() {
		return c;
	}
	public void setC(Customer c) {
		this.c = c;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserInfo [userInfoid=" + userInfoid + ", c=" + c + ", password=" + password + "]";
	}
	
	
}
