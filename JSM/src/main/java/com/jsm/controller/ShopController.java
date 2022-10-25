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

import com.jsm.dto.AuthenticationRequest;
import com.jsm.dto.AuthenticationResponse;
import com.jsm.dto.VaildatingDTO;
import com.jsm.exception.DataNotFoundException;
import com.jsm.exception.InvalidCredentialException;
import com.jsm.exception.WrongFormatException;
import com.jsm.service.CustomerService;
import com.jsm.service.ShopService;
import com.jsm.util.JwtUtil;

@RestController
@RequestMapping("/shop")
public class ShopController {
	@Autowired
	ShopService service;
	@Autowired
	CustomerService cservice;
    @Autowired
	JwtUtil jwtTokenUtil;
    @Autowired
	VaildatingDTO vaildatingDTO;
    @Autowired
	Environment env;

//msg: Validate token of shop
//input:  String token
//output: String msg
	@GetMapping( path = "/validate")
	public ResponseEntity<VaildatingDTO> validatingAuthorizationToken(@RequestHeader(name = "Authorization" ) String tokenDup) throws WrongFormatException, DataNotFoundException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = service.getShopByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					vaildatingDTO.setValidStatus(true);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
//	@GetMapping("/all")
//	public List<?> getall(){
//		return service.findAll();
//	}
	
//msg: Login shop
//input:  AuthenticationRequest object
//output: AuthenticationResponse object
	@PostMapping("/login")
	public ResponseEntity<?> loginShop(@RequestBody AuthenticationRequest req) throws InvalidCredentialException, DataNotFoundException {		
		 UserDetails userDetails=service.login(req);
		return  new ResponseEntity<>(
				new AuthenticationResponse(req.getEmail(), jwtTokenUtil.generateToken(userDetails)),HttpStatus.OK);
	}
	
//msg: Logout a shop
//input:  String token
//output: String msg
	@GetMapping("/logout")
	public  ResponseEntity<?> logoutCus(@RequestHeader(name = "Authorization" ) String tokenDup) throws  WrongFormatException, DataNotFoundException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = service.getShopByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(env.getProperty("logout"),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
//msg: Get all customers
//input:  String token
//output: List of customer objects
	@GetMapping("/allCustomers")
	public ResponseEntity<?> getallCust(@RequestHeader(name = "Authorization" ) String tokenDup) throws DataNotFoundException, WrongFormatException{
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = service.getShopByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(cservice.findAll(),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
		
	}
}
