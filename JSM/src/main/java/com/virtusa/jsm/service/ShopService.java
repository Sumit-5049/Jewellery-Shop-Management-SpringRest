package com.virtusa.jsm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.virtusa.jsm.dao.ShopRepository;
import com.virtusa.jsm.dto.AuthenticationRequest;
import com.virtusa.jsm.dto.Customer;
import com.virtusa.jsm.dto.Shop;
import com.virtusa.jsm.exception.DataNotFoundException;
import com.virtusa.jsm.exception.InvalidCredentialException;

@Service
public class ShopService {
	
	@Autowired
	ShopRepository dao;
	@Autowired
	Environment env;
	Log log= LogFactory.getLog(CustomerService.class);
	
	public List<?> findAll() {
		return dao.findAll();		
	}

	public Shop add(Shop s) {
		return dao.save(s);
	}

//	public Shop login(String email, String pass) throws InvalidCredentialException, DataNotFoundException {
//		
//		Shop s=null;
//		s=dao.findByEmail(email);
//		if(s!=null) {
//			if(s.getPassword().equals(pass)) {
//				log.info(env.getProperty("loginshop"));
//				return s;}
//			else {
//				log.error(env.getProperty("invalid"));
//				throw new InvalidCredentialException(env.getProperty("invalid"));}
//			}
//		log.error(env.getProperty("invalid")+email);
//		throw new DataNotFoundException(env.getProperty("invalid")+email);
//	}

	public UserDetails login(AuthenticationRequest req) throws InvalidCredentialException, DataNotFoundException {
		Shop s=new Shop();
		s=dao.findByEmail(req.getEmail());
		if(s!=null) {
			if(s.getPassword().equals(req.getPassword())) {
				log.info(env.getProperty("loginshop"));
				return new User(s.getEmail(),s.getPassword(), new ArrayList<>());}
			else {
				log.error(env.getProperty("invalid"));
				throw new InvalidCredentialException(env.getProperty("invalid"));}
			}
		log.error(env.getProperty("invalid")+req.getEmail());
		throw new DataNotFoundException(env.getProperty("invalid")+req.getEmail());
	}

	public UserDetails getShopByEmail(String req) throws DataNotFoundException {
		 Shop s=dao.findByEmail(req);
		if(s!=null) {
			return new User(s.getEmail(),s.getPassword(), new ArrayList<>());
		}
		log.error(env.getProperty("invalid")+req);
		throw new DataNotFoundException(env.getProperty("invalid")+req);
	}
		
	
	
	
}
