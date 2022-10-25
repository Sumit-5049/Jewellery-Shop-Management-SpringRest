package com.jsm.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.jsm.dao.QorderRepository;
import com.jsm.dto.Bill;
import com.jsm.dto.Cart;
import com.jsm.dto.Product;
import com.jsm.dto.QOrder;
import com.jsm.exception.DataNotFoundException;
import com.jsm.exception.StockUnavailableException;

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
	
//msg:Purches the products & make a bill
//input:String customer_email, QOrder object
//output: Bill object
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
			Double t=Double.parseDouble(String.format("%.2f", a.getP().getPrice()*qnt));
			total+=t;
			a.setPrice(t);
		}
		Double tax=Double.parseDouble(env.getProperty("tax"))*total/100;
		o.setTax(Double.parseDouble(String.format("%.2f", tax)));
		o.setTotal(total+tax);
	
		odao.save(o);
		
		Bill b=bservice.generate(odao.findById(o.getOrderid()).get(),cservice.getId(email));
		
		log.info(env.getProperty("purched"));
		return b;	
	}

//msg:Get all Qorders
//input:
//output: List of QOrder objects
	public Object getAll() {
		return odao.findAll();
	}
	
}