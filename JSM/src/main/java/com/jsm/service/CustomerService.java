package com.jsm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jsm.dao.CustomerRepository;
import com.jsm.dto.AuthenticationRequest;
import com.jsm.dto.Customer;
import com.jsm.dto.UserInfo;
import com.jsm.exception.DataNotFoundException;
import com.jsm.exception.DuplicateException;
import com.jsm.exception.InvalidCredentialException;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository dao;
	@Autowired
	UserInfoService uservice;
	@Autowired
	Environment env;
	Log log= LogFactory.getLog(CustomerService.class);

//msg:Login
//input:AuthenticationRequest object
//output:UserDetails object
	public UserDetails login(AuthenticationRequest req) throws InvalidCredentialException, DataNotFoundException {
		Customer c=null;
		c=dao.findByEmail(req.getEmail());
		if(c!=null) {
			if(uservice.login(c,req.getPassword())) {
				log.info(env.getProperty("logincus")+req.getEmail());
				return new User(c.getEmail(),req.getPassword(), new ArrayList<>());}
			else {
				log.error(env.getProperty("invalid"));
				throw new InvalidCredentialException(env.getProperty("invalid"));}
			}
		log.error(env.getProperty("notpresent")+req.getEmail());
		throw new DataNotFoundException(env.getProperty("notpresent")+req.getEmail());
	}

//msg:All Customers
//input:
//output:List of Customer objects
	public List<?> findAll() {
		return dao.findAll();		
	}
	
//msg:Customer by id
//input:int customer_id
//output:Customer object
	public Customer getById(int i) {
		return dao.findById(i).get();
	}

//msg:Get Customer by email
//input:String customer_email
//output:UsterDetails object
	public UserDetails getByEmail(String extractUsername) throws DataNotFoundException {
		Customer c=dao.findByEmail(extractUsername);
		if(c!=null) {
			return new User(c.getEmail(),uservice.getPassword(c), new ArrayList<>());
		}
		log.error(env.getProperty("invalid")+extractUsername);
		throw new DataNotFoundException(env.getProperty("invalid")+extractUsername);
	}

//msg:Get Customer_id
//input:String customer_email
//output: int customer_id
	public int getId(String email) {
		return dao.findByEmail(email).getCustomerid();
	}

//msg:Get Customer by email
//input:String customer_email
//output: Customer object
	public Customer getCByEmail(String email) {
		return dao.findByEmail(email);
	}

//msg:Register new Customer
//input:UserInfo object
//output:msg
	public Object register(UserInfo u) throws DuplicateException {
		if(!dao.existsByEmail(u.getC().getEmail())) {
			return uservice.register(u);
		}
		log.error(env.getProperty("duplicate")+u.getC().getEmail());
		throw new DuplicateException(env.getProperty("duplicate")+u.getC().getEmail());		
	}
	
}
