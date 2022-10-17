package com.virtusa.jsm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.virtusa.jsm.dao.CustomerRepository;
import com.virtusa.jsm.dto.AuthenticationRequest;
import com.virtusa.jsm.dto.Customer;
import com.virtusa.jsm.dto.Shop;
import com.virtusa.jsm.dto.UserInfo;
import com.virtusa.jsm.exception.DataNotFoundException;
import com.virtusa.jsm.exception.DuplicateException;
import com.virtusa.jsm.exception.InvalidCredentialException;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository dao;
	@Autowired
	UserInfoService uservice;
	@Autowired
	Environment env;
	Log log= LogFactory.getLog(CustomerService.class);

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

	
	public List<?> findAll() {
		return dao.findAll();		
	}

	public Customer getById(int i) {
		return dao.findById(i).get();
	}


	public UserDetails getByEmail(String extractUsername) throws DataNotFoundException {
		Customer c=dao.findByEmail(extractUsername);
		if(c!=null) {
			return new User(c.getEmail(),uservice.getPassword(c), new ArrayList<>());
		}
		log.error(env.getProperty("invalid")+extractUsername);
		throw new DataNotFoundException(env.getProperty("invalid")+extractUsername);
	}

	public int getId(String email) {
		return dao.findByEmail(email).getCustomerid();
	}


	public Customer getCByEmail(String email) {
		return dao.findByEmail(email);
	}


	public Object register(UserInfo u) throws DuplicateException {
		if(!dao.existsByEmail(u.getC().getEmail())) {
			return uservice.register(u);
		}
		log.error(env.getProperty("duplicate")+u.getC().getEmail());
		throw new DuplicateException(env.getProperty("duplicate")+u.getC().getEmail());
			
	}



	
}
