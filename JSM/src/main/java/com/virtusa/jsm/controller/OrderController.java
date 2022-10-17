package com.virtusa.jsm.controller;

import java.util.List;

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

import com.virtusa.jsm.dto.Bill;
import com.virtusa.jsm.dto.QOrder;
import com.virtusa.jsm.dto.VaildatingDTO;
import com.virtusa.jsm.exception.DataNotFoundException;
import com.virtusa.jsm.exception.StockUnavailableException;
import com.virtusa.jsm.exception.WrongFormatException;
import com.virtusa.jsm.service.CustomerService;
import com.virtusa.jsm.service.OrderService;
import com.virtusa.jsm.util.JwtUtil;

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
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllProducts() throws DataNotFoundException {
		return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
	}
}
