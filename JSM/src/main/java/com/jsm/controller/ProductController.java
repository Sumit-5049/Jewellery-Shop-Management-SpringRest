package com.jsm.controller;

import java.util.List;

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

import com.jsm.dto.Product;
import com.jsm.dto.VaildatingDTO;
import com.jsm.exception.DataNotFoundException;
import com.jsm.exception.WrongFormatException;
import com.jsm.service.ProductService;
import com.jsm.service.ShopService;
import com.jsm.util.JwtUtil;

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
	     
//msg:Get the current prices of Gold & Silver
//input: 
//output: List of Doubles
    @GetMapping("/rates")
	public ResponseEntity<?> getPrices() throws DataNotFoundException {
		String url="http://localhost:9091/gsprice";
		List<Double> l=templet.getForObject(url, List.class);
		return new ResponseEntity<>(l,HttpStatus.OK);
	}
    
//msg:Get all Products
//input:
//output: List of Product objects
    @GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProducts() throws DataNotFoundException {
		String url="http://localhost:9091/gsprice";
		List<Double> l=templet.getForObject(url, List.class);
		return new ResponseEntity<>(service.getAll(l),HttpStatus.OK);
	}
    
//msg:Get all Products by sort
//input: String sort
//output: List of Product objects
    @GetMapping("/all/{p1}")
   	public ResponseEntity<List<Product>> getAllProductSort(@PathVariable("p1") String sort) throws DataNotFoundException {
   		String url="http://localhost:9091/gsprice";
   		List<Double> l=templet.getForObject(url, List.class);
   		return new ResponseEntity<>(service.getAllSort(l,sort),HttpStatus.OK);
   	}
	
//msg:Get all Products by material
//input: String material
//output: List of Product objects
	@GetMapping("/allbymaterail/{p1}")
	public ResponseEntity<?> getAllProductsM(@PathVariable("p1") String material) throws DataNotFoundException {
		return new ResponseEntity<>(service.getAllByMaterial(material),HttpStatus.OK);
	}
	
//msg:Get all Products for material by sort
//input: String material, String sort
//output: List of Product objects
	@GetMapping("/allbymaterail/{p1}/{p2}")
	public ResponseEntity<?> getAllProductsM(@PathVariable("p1") String material,@PathVariable("p2") String sort) throws DataNotFoundException {
		return new ResponseEntity<>(service.getAllByMaterialSort(material,sort),HttpStatus.OK);
	}
	
//msg:Get all Products by type
//input: String type
//output: List of Product objects
	@GetMapping("/allbytype/{p1}")
	public ResponseEntity<?> getAllProductsT(@PathVariable("p1") String type) throws DataNotFoundException {
		return new ResponseEntity<>(service.getAllByType(type),HttpStatus.OK);
	}
	
//msg:Get all Products for type by sort
//input: STring type, String sort
//output: List of Product objects
	@GetMapping("/allbytype/{p1}/{p2}")
	public ResponseEntity<?> getAllProductsT(@PathVariable("p1") String type,@PathVariable("p2") String sort) throws DataNotFoundException {
		return new ResponseEntity<>(service.getAllByTypeSort(type,sort),HttpStatus.OK);
	}
	
//msg:Get a Product by id
//input: int product_id
//output: Product object
	@GetMapping("/aproduct/{p}")
	public ResponseEntity<?> getProduct(@PathVariable("p") int id) throws DataNotFoundException {
		return new ResponseEntity<>(service.getById(id),HttpStatus.OK);
	}
	
//msg:Add new product
//input: Product object, String token
//output: String msg
	@PostMapping("/add")
	public ResponseEntity<?> register(@RequestHeader(name = "Authorization" ) String tokenDup,@RequestBody Product p) throws WrongFormatException, DataNotFoundException {
		String token = tokenDup.substring(7);
		if(jwtTokenUtil.isTokenInFormat(token)){
			UserDetails user;
			user = sservice.getShopByEmail(jwtTokenUtil.extractUsername(token));
			if(user!=null) {
				if (jwtTokenUtil.validateToken(token, user)) {
					return new ResponseEntity<>(service.add(p),HttpStatus.OK);
				}  else {
					vaildatingDTO.setValidStatus(false);
					return new ResponseEntity<>(vaildatingDTO, HttpStatus.FORBIDDEN);
				}
			}
		}
		throw new WrongFormatException(env.getProperty("wrongJWT"));
	}
	
//msg:Modify a product
//input: Product object, String token
//output: String msg
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
