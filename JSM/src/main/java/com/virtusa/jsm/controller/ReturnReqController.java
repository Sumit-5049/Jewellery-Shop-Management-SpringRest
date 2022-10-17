package com.virtusa.jsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.jsm.dto.Bill;
import com.virtusa.jsm.dto.Product;
import com.virtusa.jsm.dto.QOrder;
import com.virtusa.jsm.dto.ReturnReq;
import com.virtusa.jsm.dto.VaildatingDTO;
import com.virtusa.jsm.exception.DataNotFoundException;
import com.virtusa.jsm.exception.NotPossibleException;
import com.virtusa.jsm.exception.StockUnavailableException;
import com.virtusa.jsm.exception.WrongFormatException;
import com.virtusa.jsm.service.CustomerService;
import com.virtusa.jsm.service.ReturnReqService;
import com.virtusa.jsm.service.ShopService;
import com.virtusa.jsm.util.JwtUtil;


@RestController
@RequestMapping("/return")
public class ReturnReqController {
	
	@Autowired
	ReturnReqService service;
	@Autowired
	CustomerService cservice;
	@Autowired
	ShopService sservice;
	@Autowired
	JwtUtil jwtTokenUtil;
    @Autowired
	VaildatingDTO vaildatingDTO;
    @Autowired
	Environment env;
	
	@PostMapping("/add")
	public ResponseEntity<?> register(@RequestBody ReturnReq r,@RequestHeader(name = "Authorization" ) String tokenDup) throws StockUnavailableException, DataNotFoundException, WrongFormatException, NotPossibleException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			user=cservice.getByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.addReturnReq(r,user.getUsername()),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
	@GetMapping("/allcus")
	public ResponseEntity<?> getAllCusBills(@RequestHeader(name = "Authorization" ) String tokenDup) throws DataNotFoundException, WrongFormatException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = cservice.getByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.getAllCus(user.getUsername()),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
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
	
	@GetMapping("/get/{p}")
	public ResponseEntity<?> getById(@RequestHeader(name = "Authorization" ) String tokenDup,@PathVariable("p")int id) throws DataNotFoundException, WrongFormatException {
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
	
	@GetMapping("/cus/{p}")
	public ResponseEntity<?> getByIdCus(@RequestHeader(name = "Authorization" ) String tokenDup,@PathVariable("p")int id) throws DataNotFoundException, WrongFormatException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			user = cservice.getByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.getByIdCus(id,user.getUsername()),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
	@GetMapping("/allByStatus/{p}")
	public ResponseEntity<?> getAllReturnReqStatus(@RequestHeader(name = "Authorization" ) String tokenDup,@PathVariable("p")String status) throws DataNotFoundException, WrongFormatException {
		 
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = sservice.getShopByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.getAllByStatus(status),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
	@GetMapping("/allCusByStatus/{p}")
	public ResponseEntity<?> getAllByStatusCus(@RequestHeader(name = "Authorization" ) String tokenDup,@PathVariable("p")String status) throws DataNotFoundException, WrongFormatException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user ;
			 user = cservice.getByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.getAllByStatusCus(user.getUsername(),status),HttpStatus.OK);
				} else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}  
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
	@PutMapping("/modify")
	public ResponseEntity<?> updateProduct(@RequestBody ReturnReq r,@RequestHeader(name = "Authorization" ) String tokenDup) throws DataNotFoundException, WrongFormatException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user;
			user = sservice.getShopByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.updateReturnReq(r),HttpStatus.OK);
				}  else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
}
