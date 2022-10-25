package com.jsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsm.dto.Bill;
import com.jsm.dto.QOrder;
import com.jsm.dto.VaildatingDTO;
import com.jsm.exception.DataNotFoundException;
import com.jsm.exception.StockUnavailableException;
import com.jsm.exception.WrongFormatException;
import com.jsm.service.CustomerService;
import com.jsm.service.OrderService;
import com.jsm.util.JwtUtil;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	OrderService service;
	@Autowired
	CustomerService cservice;
	@Autowired
	JwtUtil jwtTokenUtil;
    @Autowired
	VaildatingDTO vaildatingDTO;
    @Autowired
	Environment env;
    
//msg:Purches the products
//input: token object,QOrder object
//output: Bill object
	@PostMapping("/purches")
	public ResponseEntity<?> register(@RequestBody QOrder o,@RequestHeader(name = "Authorization" ) String tokenDup) throws StockUnavailableException, DataNotFoundException, WrongFormatException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			user=cservice.getByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					Bill b;
					b=service.purches(o,jwtTokenUtil.extractUsername(token));
					return new ResponseEntity<>(b,HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
		
	}
	
//msg:Get all QOrders
//input: 
//output: List of QOder objects
	@GetMapping("/all")
	public ResponseEntity<?> getAllProducts() throws DataNotFoundException {
		return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
	}
}