package com.virtusa.jsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.jsm.dto.VaildatingDTO;
import com.virtusa.jsm.exception.DataNotFoundException;
import com.virtusa.jsm.exception.WrongFormatException;
import com.virtusa.jsm.service.BillService;
import com.virtusa.jsm.service.CustomerService;
import com.virtusa.jsm.service.ShopService;
import com.virtusa.jsm.util.JwtUtil;

@RestController
@RequestMapping("/bill")
public class BillController {
	
	@Autowired
	BillService service;
	@Autowired
	ShopService sservice;
	@Autowired
	CustomerService cservice;
	@Autowired
	JwtUtil jwtTokenUtil;
    @Autowired
	VaildatingDTO vaildatingDTO;
    @Autowired
 	Environment env;
    
//msg:Get all Bills of a customer
//input: token object
//output: List Bill objects
	@GetMapping("/all")
	public ResponseEntity<?> getAllCusBills(@RequestHeader(name = "Authorization" ) String tokenDup) throws DataNotFoundException, WrongFormatException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = cservice.getByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.getAllC(user.getUsername()),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
//msg:Get all Bills of a customer by sort
//input: token object
//output: List Bill objects
	@GetMapping("/all/{p}")
	public ResponseEntity<?> getAllCusrBillsSort(@RequestHeader(name = "Authorization" ) String tokenDup,@PathVariable("p") String sort) throws DataNotFoundException, WrongFormatException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = cservice.getByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.getAllCS(user.getUsername(),sort),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
//msg:Get all Bills of Shop
//input: token object
//output: List Bill objects
	@GetMapping("/allShop")
	public ResponseEntity<?> getAllBills(@RequestHeader(name = "Authorization" ) String tokenDup) throws DataNotFoundException, WrongFormatException { 
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = sservice.getShopByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
//msg:Get all Bills of Shop by sort
//input: token object
//output: List Bill objects
	@GetMapping("/allShop/{p}")
	public ResponseEntity<?> getAllBills(@RequestHeader(name = "Authorization" ) String tokenDup,@PathVariable("p") String sort) throws DataNotFoundException, WrongFormatException {
		 
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = sservice.getShopByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.getAllSort(sort),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
//msg:Get a Bill by id for shop
//input: token object, int bill_id
//output:  Bill object
	@GetMapping("/Shop/{p}")
	public ResponseEntity<?> getById(@RequestHeader(name = "Authorization" ) String tokenDup,@PathVariable("p") int id) throws DataNotFoundException, WrongFormatException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = sservice.getShopByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.getById(id),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
//msg:Get a List of  Bills of particular customer
//input: token object, string customer_email
//output: List of Bill objects
	@GetMapping("/Customer/{p}")
	public ResponseEntity<?> getAllProductCS(@RequestHeader(name = "Authorization" ) String tokenDup,@PathVariable("p") String email) throws DataNotFoundException, WrongFormatException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			
			UserDetails user ;
			 user = sservice.getShopByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.getAllC(email),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
		}}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
//msg:Get a Bill by id for customer
//input: token object, int bill_id
//output:  Bill object
	@GetMapping("/customer/{p}")
	public ResponseEntity<?> getByIdC(@RequestHeader(name = "Authorization" ) String tokenDup,@PathVariable("p") int id) throws DataNotFoundException, WrongFormatException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = cservice.getByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.getByIdC(id,user.getUsername()),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
}
