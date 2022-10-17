package com.virtusa.jsm.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.virtusa.jsm.dao.UserInfoRepository;
import com.virtusa.jsm.dto.Customer;
import com.virtusa.jsm.dto.UserInfo;

@Service
public class UserInfoService {
	
	@Autowired
	UserInfoRepository dao;
	
	@Autowired
	Environment env;
	Log log= LogFactory.getLog(UserInfoService.class);

	
	public List<?> findAll() {
		return dao.findAll();		
	}

	public String register(UserInfo u) {
		dao.save(u);
		log.info(env.getProperty("registered")+u.getC().getEmail());
		return env.getProperty("registered")+u.getC().getEmail();
	}

	public boolean login(Customer c, String pass) {
		UserInfo u=dao.findByC(c);
		if(u.getPassword().equals(pass))
			return true;
		else
			return false;
	}

	public String getPassword(Customer c) {
		// TODO Auto-generated method stub
		return dao.findByC(c).getPassword();
	}

	
	
}
