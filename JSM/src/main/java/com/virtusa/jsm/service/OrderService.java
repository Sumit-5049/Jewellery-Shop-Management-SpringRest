package com.virtusa.jsm.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.virtusa.jsm.dao.QorderRepository;
import com.virtusa.jsm.dto.Bill;
import com.virtusa.jsm.dto.Cart;
import com.virtusa.jsm.dto.Product;
import com.virtusa.jsm.dto.QOrder;
import com.virtusa.jsm.exception.DataNotFoundException;
import com.virtusa.jsm.exception.StockUnavailableException;

@Service
public class OrderService {

	@Autowired
	QorderRepository odao;
	@Autowired
	Environment env;
	@Autowired
	ProductService pservice;
	@Autowired
	BillService bservice;
	@Autowired
	CustomerService cservice;
	Log log= LogFactory.getLog(OrderService.class);
	
	public Bill purches(QOrder o,String email) throws StockUnavailableException, DataNotFoundException {
		List<Cart> c=o.getCarts();
		Double total=0.0;
		for (Iterator<Cart> iterator = c.iterator(); iterator.hasNext();) {
			Cart a = (Cart) iterator.next();
			int qnt=a.getPurchesquantity();
			int pid=a.getP().getProductid();
			Product p=pservice.getById(pid);
			int aqut=p.getQuantity();
			if(aqut-qnt<=0||aqut<=0) {
				log.error(env.getProperty("lessStock")+aqut+", productid: "+p.getProductid());
				throw new DataNotFoundException(env.getProperty("lessStock")+aqut+", productid: "+p.getProductid());
			}
			p.setQuantity(aqut-qnt);
			a.setP(p);
			a.setRateper10gm(p.getRateper10gm());
			Double t=a.getP().getPrice()*qnt;
			total+=t;
			a.setPrice(t);
		}
		Double tax=Double.parseDouble(env.getProperty("tax"))*total/100;
		o.setTax(tax);
		o.setTotal(total+tax);
	
		odao.save(o);
		
		Bill b=bservice.generate(odao.findById(o.getOrderid()).get(),cservice.getId(email));
		
		log.info(env.getProperty("purched"));
		return b;	}

	public Object getAll() {
		return odao.findAll();
	}
	
	
	
	
}
