package com.virtusa.jsm.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.virtusa.jsm.dao.BillRepository;
import com.virtusa.jsm.dao.QorderRepository;
import com.virtusa.jsm.dto.Bill;
import com.virtusa.jsm.dto.Customer;
import com.virtusa.jsm.dto.QOrder;
import com.virtusa.jsm.exception.DataNotFoundException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class BillService {
	@Autowired
	BillRepository dao;
	@Autowired
	QorderRepository odao;
	@Autowired
	CustomerService cservice;
	@Autowired
	Environment env;
	Log log= LogFactory.getLog(BillService.class);
	
//msg:Generates a new Bill
//input:Qoredr object & int customer_id
//output:Bill object
	public Bill generate(QOrder o, int i) {
		Customer c=cservice.getById(i);
		LocalDateTime now = LocalDateTime.now();
        Timestamp dateTime = Timestamp.valueOf(now);
        Bill b=new Bill();
        b.setCust(c);
        b.setDateTime(dateTime);
        b.setord(o);
        Bill s=dao.save(b);
        log.info(env.getProperty("billGenerate"));
        return s;
	}
	
//msg:All Bills
//input:
//output:List of Bill objects
	public Object getAll() throws DataNotFoundException {
		List<Bill> list=null;
		
		list= dao.findAll();
		if(!list.isEmpty()) {
			 log.error(env.getProperty("displayBills"));
			return list;}
		else {
			 log.error(env.getProperty("noBill"));
			throw new DataNotFoundException(env.getProperty("noBill"));}
	}

//msg:All Bills for a customer
//input:String customer_rmail
//output:List if Bill Objects
	public Object getAllC(String email) throws DataNotFoundException {
		List<Bill> list=null;
		Customer c=cservice.getCByEmail(email);
		list= dao.findAllByCust(c);
		if(!list.isEmpty()) {
			 log.error(env.getProperty("displayBills"));
			return list;}
		else {
			 log.error(env.getProperty("noBill"));
			throw new DataNotFoundException(env.getProperty("noBill"));}
	}

//msg:Get Bill By Id
//input:int bill_id
//output:Bill object
	public Bill getById(int id) throws DataNotFoundException {
		if(dao.existsById(id)) {
			 log.error(env.getProperty("displayBill1"));
			return dao.findById(id).get();}
		else {
			 log.error(env.getProperty("noBill"));
			throw new DataNotFoundException(env.getProperty("noBill"));}
	}
	
//msg:A Bill For a Customer
//input:String customer_email,int bill_id
//output:Bill Object
	public Object getByIdC(int id, String email) throws DataNotFoundException {
		Bill b;
		Customer c=cservice.getCByEmail(email);
		if(dao.existsById(id)) {
			b=this.getById(id);
			if(b.getCust().equals(c)) {
				 log.error(env.getProperty("displayBill1"));
				return b;}
				 log.error(env.getProperty("noBill"));
				throw new DataNotFoundException(env.getProperty("noBill"));
			}
		log.error(env.getProperty("noBill"));
		throw new DataNotFoundException(env.getProperty("noBill"));
		}

//msg:All Bills in sorting
//input:String sort
//output:List if Bill Objects
	public Object getAllSort(String sort) throws DataNotFoundException {
		List<Bill> list=(List<Bill>) this.getAll();
		if(sort.equals("asc"))
			list.sort((o1, o2) -> Double.compare(o2.getord().getTotal(), o1.getord().getTotal()));
		else if(sort.equals("desc"))
			list.sort((o1, o2) -> Double.compare(o1.getord().getTotal(), o2.getord().getTotal()));
		return list;
	}

//msg:All Bills in sorting for a customer
//input:String sort
//output:List if Bill Objects
	public Object getAllCS(String email, String sort) throws DataNotFoundException {
		List<Bill> list=(List<Bill>) this.getAllC(email);
		if(sort.equals("asc"))
			list.sort((o1, o2) -> Double.compare(o2.getord().getTotal(), o1.getord().getTotal()));
		else if(sort.equals("desc"))
			list.sort((o1, o2) -> Double.compare(o1.getord().getTotal(), o2.getord().getTotal()));
		return list;
	}

//msg:Check Bill is exit or not
//input:int bill_id
//output:Boolean value
	public boolean exitById(int billid) throws DataNotFoundException {
		if(dao.existsById(billid)) {
			 log.error(env.getProperty("displayBill1"));
			return true;}
		else {
			 log.error(env.getProperty("noBill"));
			throw new DataNotFoundException(env.getProperty("noBill"));}
	}
	
}
