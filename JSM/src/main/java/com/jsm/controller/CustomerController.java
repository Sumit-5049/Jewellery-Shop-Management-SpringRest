package com.jsm.controller;

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

import com.jsm.dto.AuthenticationRequest;
import com.jsm.dto.AuthenticationResponse;
import com.jsm.dto.UserInfo;
import com.jsm.dto.VaildatingDTO;
import com.jsm.exception.DataNotFoundException;
import com.jsm.exception.DuplicateException;
import com.jsm.exception.InvalidCredentialException;
import com.jsm.exception.WrongFormatException;
import com.jsm.service.CustomerService;
import com.jsm.service.UserInfoService;
import com.jsm.util.JwtUtil;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService cservice;
	@Autowired
	UserInfoService uservice;
	@Autowired
	JwtUtil jwtTokenUtil;
    @Autowired
	VaildatingDTO vaildatingDTO;
	@Autowired
	Environment env;
	
//msg:Get all customers
//input: 
//output: List of Customer objects
	@GetMapping("/all")
	public List<?> getall(){
		return uservice.findAll();
	}
	
//msg:Login by the customer
//input: AuthenticationRequest object
//output: AuthenticationResponse object
	@PostMapping("/login")
	public  ResponseEntity<?> loginCus(@RequestBody AuthenticationRequest req) throws InvalidCredentialException, DataNotFoundException {
		 UserDetails userDetails=cservice.login(req);
			return  new ResponseEntity<>(
					new AuthenticationResponse(req.getEmail(), jwtTokenUtil.generateToken(userDetails)),HttpStatus.OK);
	}
	
//msg:Add new customer
//input: UserInfo object
//output: string msg
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserInfo u) throws DuplicateException {
		return new ResponseEntity<>(cservice.register(u),HttpStatus.OK);
	}
	
//msg:Logout by a customer
//input: token object
//output: String msg
	@GetMapping("/logout")
	public  ResponseEntity<?> logoutCus(@RequestHeader(name = "Authorization" ) String tokenDup) throws  WrongFormatException, DataNotFoundException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = cservice.getByEmail(jwtTokenUtil.extractUsername(token));
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
	
//msg:Validate token for a customer
//input: token object
//output: String msg
	@GetMapping( path = "/validate")
	public ResponseEntity<VaildatingDTO> validatingAuthorizationToken(@RequestHeader(name = "Authorization" ) String tokenDup) throws WrongFormatException, DataNotFoundException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = cservice.getByEmail(jwtTokenUtil.extractUsername(token));
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
}