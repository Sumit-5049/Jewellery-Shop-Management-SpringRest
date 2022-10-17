package com.virtusa.jsm.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.web.client.RestTemplate;

import com.virtusa.jsm.dto.Product;
import com.virtusa.jsm.dto.UserInfo;
import com.virtusa.jsm.dto.VaildatingDTO;
import com.virtusa.jsm.exception.DataNotFoundException;
import com.virtusa.jsm.exception.InvalidCredentialException;
import com.virtusa.jsm.exception.WrongFormatException;
import com.virtusa.jsm.service.ProductService;
import com.virtusa.jsm.service.ShopService;
import com.virtusa.jsm.util.JwtUtil;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductService service;
	@Autowired
	ShopService sservice;
	@Autowired
	JwtUtil jwtTokenUtil;
    @Autowired
	VaildatingDTO vaildatingDTO;
    @Autowired
	Environment env;
    @Autowired
    RestTemplate templet;
	     
    @GetMapping("/rates")
	public ResponseEntity<?> getPrices() throws DataNotFoundException {
		String url="http://localhost:9091/gsprice";
		List<Double> l=templet.getForObject(url, List.class);
		return new ResponseEntity<>(l,HttpStatus.OK);
	}
    
    @GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProducts() throws DataNotFoundException {
		String url="http://localhost:9091/gsprice";
		List<Double> l=templet.getForObject(url, List.class);
		return new ResponseEntity<>(service.getAll(l),HttpStatus.OK);
	}
    
    @GetMapping("/all/{p1}")
   	public ResponseEntity<List<Product>> getAllProductSort(@PathVariable("p1") String sort) throws DataNotFoundException {
   		String url="http://localhost:9091/gsprice";
   		List<Double> l=templet.getForObject(url, List.class);
   		return new ResponseEntity<>(service.getAllSort(l,sort),HttpStatus.OK);
   	}
	
	@GetMapping("/allbymaterail/{p1}")
	public ResponseEntity<?> getAllProductsM(@PathVariable("p1") String material) throws DataNotFoundException {
		return new ResponseEntity<>(service.getAllByMaterial(material),HttpStatus.OK);
	}
	
	@GetMapping("/allbymaterail/{p1}/{p2}")
	public ResponseEntity<?> getAllProductsM(@PathVariable("p1") String material,@PathVariable("p2") String sort) throws DataNotFoundException {
		return new ResponseEntity<>(service.getAllByMaterialSort(material,sort),HttpStatus.OK);
	}
	
	@GetMapping("/allbytype/{p1}")
	public ResponseEntity<?> getAllProductsT(@PathVariable("p1") String type) throws DataNotFoundException {
		return new ResponseEntity<>(service.getAllByType(type),HttpStatus.OK);
	}
	
	@GetMapping("/allbytype/{p1}/{p2}")
	public ResponseEntity<?> getAllProductsT(@PathVariable("p1") String type,@PathVariable("p2") String sort) throws DataNotFoundException {
		return new ResponseEntity<>(service.getAllByTypeSort(type,sort),HttpStatus.OK);
	}
	
	@GetMapping("/aproduct/{p}")
	public ResponseEntity<?> getProduct(@PathVariable("p") int id) throws DataNotFoundException {
		return new ResponseEntity<>(service.getById(id),HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> register(@RequestBody Product p) {
		return new ResponseEntity<>(service.add(p),HttpStatus.OK);
	}
	
	@PutMapping("/modify")
	public ResponseEntity<?> updateProduct(@RequestBody Product p,@RequestHeader(name = "Authorization" ) String tokenDup) throws DataNotFoundException, WrongFormatException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user;
			user = sservice.getShopByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.updateProduct(p),HttpStatus.OK);
				}  else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
	
	
}
