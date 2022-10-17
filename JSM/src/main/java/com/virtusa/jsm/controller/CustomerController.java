package com.virtusa.jsm.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.jsm.dao.BillRepository;
import com.virtusa.jsm.dao.CustomerRepository;
import com.virtusa.jsm.dao.QorderRepository;
import com.virtusa.jsm.dao.ShopRepository;
import com.virtusa.jsm.dto.AuthenticationRequest;
import com.virtusa.jsm.dto.AuthenticationResponse;
import com.virtusa.jsm.dto.UserInfo;
import com.virtusa.jsm.dto.VaildatingDTO;
import com.virtusa.jsm.exception.DataNotFoundException;
import com.virtusa.jsm.exception.DuplicateException;
import com.virtusa.jsm.exception.InvalidCredentialException;
import com.virtusa.jsm.exception.WrongFormatException;
import com.virtusa.jsm.service.CustomerService;
import com.virtusa.jsm.service.UserInfoService;
import com.virtusa.jsm.util.JwtUtil;

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
	@GetMapping("/all")
	public List<?> getall(){
		return uservice.findAll();
	}
	
	@PostMapping("/login")
	public  ResponseEntity<?> loginCus(@RequestBody AuthenticationRequest req) throws InvalidCredentialException, DataNotFoundException {
		 UserDetails userDetails=cservice.login(req);
			return  new ResponseEntity<>(
					new AuthenticationResponse(req.getEmail(), jwtTokenUtil.generateToken(userDetails)),HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserInfo u) throws DuplicateException {
		return new ResponseEntity<>(cservice.register(u),HttpStatus.OK);
	}
	
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
}
