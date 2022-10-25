package com.jsm.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.jsm.dao.UserInfoRepository;
import com.jsm.dto.Customer;
import com.jsm.dto.UserInfo;

@Service
public class UserInfoService {
	
	@Autowired
	UserInfoRepository dao;
	
	@Autowired
	Environment env;
	Log log= LogFactory.getLog(UserInfoService.class);

//msg:Get all Users
//input:
//output:List of UserInfo objects
	public List<?> findAll() {
		return dao.findAll();		
	}

//msg:Add new User
//input:UserInfo object
//output:String msg
	public String register(UserInfo u) {
		dao.save(u);
		log.info(env.getProperty("registered")+u.getC().getEmail());
		return env.getProperty("registered")+u.getC().getEmail();
	}
	
//msg:Login
//input:Customer object, String password
//output:Boolean value
	public boolean login(Customer c, String pass) {
		UserInfo u=dao.findByC(c);
		if(u.getPassword().equals(pass))
			return true;
		else
			return false;
	}
	
//msg:Get password
//input:Customer object
//output:String password
	public String getPassword(Customer c) {
		// TODO Auto-generated method stub
		return dao.findByC(c).getPassword();
	}
}
