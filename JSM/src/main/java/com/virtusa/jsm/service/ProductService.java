package com.virtusa.jsm.service;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.virtusa.jsm.dao.ProductRepository;
import com.virtusa.jsm.dto.Product;
import com.virtusa.jsm.exception.DataNotFoundException;

@Service
public class ProductService {

	@Autowired
	ProductRepository dao;
	@Autowired
	Environment env;
	Log log= LogFactory.getLog(ProductService.class);
	
//msg:Get all Products & calculate current price
//input:List<Double> price of gold & silver
//output: List of Product objects
	public List<Product> getAll(List<Double> l) throws DataNotFoundException {
		List<Product> list=null;
		list= dao.findAll();
		if(!list.isEmpty())
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Product p = (Product) iterator.next();
//				if(p.getPrice()==0.0)
				{
					double r=0.0;
					 double x=p.getPurity();
					 String m=p.getMaterial();
					 if(m.equals("Gold"))
						 r=l.get(0);
					 else if(m.equals("Silver"))
						 r=l.get(1);
					 p.setPrice(Double.parseDouble(String.format("%.2f",(r*x*p.getWeight()/100))));
					 p.setRateper10gm(r);
					 dao.save(p);
				}
			}
			log.info(env.getProperty("displayProduct"));
			return list;
		}
		else {
			log.error(env.getProperty("emptyproduct"));
			throw new DataNotFoundException(env.getProperty("emptyproduct"));
		}
	}

//msg:Get all Products by Material
//input:String material
//output: List of Product objects
	public List<Product> getAllByMaterial(String material) throws DataNotFoundException {
		List<Product> list=null;
		list= dao.findAllByMaterial(material);
		if(!list.isEmpty()) {
			log.info(env.getProperty("displayProductMat")+material);
			return list;}
		else {
			log.error(env.getProperty("emptyproductmat")+material);
			throw new DataNotFoundException(env.getProperty("emptyproductmat")+material);}
	}

//msg:Get all Products By type
//input:String type
//output: List of Product objects
	public List<Product> getAllByType(String type) throws DataNotFoundException {
		List<Product> list=null;
		list= dao.findAllByType(type);
		if(!list.isEmpty()) {
			log.info(env.getProperty("displayProductMat")+type);
			return list;}
		else {
			log.error(env.getProperty("emptyproductmat")+type);
			throw new DataNotFoundException(env.getProperty("emptyproductmat")+type);}
	}

//msg:Add new Product
//input:Product object
//output: String msg
	public String add(Product p) {
		dao.save(p);
		log.info(env.getProperty("padded"));
		return env.getProperty("padded");
	}
	
//msg:Update a Product
//input:Product object
//output: String msg
	public String updateProduct(Product p) {
		dao.save(p);
		log.info(env.getProperty("pupdated"));
		return env.getProperty("pupdated");
	}

//msg:Get a product
//input:int product_id
//output: Product object
	public Product getById(int pid) throws DataNotFoundException {
		Optional<?> p;
		p=dao.findById(pid);
		if(!p.isEmpty()) {
			log.info(env.getProperty("displayProduct1"));
			return dao.findById(pid).get();}
		else {
			log.error(env.getProperty("emptyproduct")+pid);
			throw new DataNotFoundException(env.getProperty("emptyproduct")+pid);}
	}

//msg:Get all Products by sort
//input: string sort,List<Double> price of gold & silver
//output: List of Product objects
	public List<Product> getAllSort(List<Double> l, String sort) throws DataNotFoundException {
		List<Product> list=this.getAll(l);
		if(sort.equals("asc"))
			list.sort(Comparator.comparingDouble(Product::getPrice).reversed());
		else if(sort.equals("desc"))
			list.sort(Comparator.comparingDouble(Product::getPrice));
		return list;
	}
	
//msg:Get all Products by Material & sort
//input:String material, string sort
//output: List of Product objects
	public List<Product> getAllByMaterialSort(String material, String sort) throws DataNotFoundException {
		List<Product> list=this.getAllByMaterial(material);
		if(sort.equals("asc"))
			list.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
		else if(sort.equals("desc"))
			list.sort(Comparator.comparingDouble(Product::getPrice));
		return list;
	}

//msg:Get all Products by Type & sort
//input:String material, string sort
//output: List of Product objects
	public Object getAllByTypeSort(String type, String sort) throws DataNotFoundException {
		List<Product> list=this.getAllByType(type);
		if(sort.equals("asc"))
			list.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
		else if(sort.equals("desc"))
			list.sort(Comparator.comparingDouble(Product::getPrice));
		return list;
	}
	
}
