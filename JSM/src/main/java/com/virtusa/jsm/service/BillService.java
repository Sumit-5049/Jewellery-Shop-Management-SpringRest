package com.virtusa.jsm.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.virtusa.jsm.dao.BillRepository;
import com.virtusa.jsm.dao.QorderRepository;
import com.virtusa.jsm.dto.Bill;
import com.virtusa.jsm.dto.Customer;
import com.virtusa.jsm.dto.Product;
import com.virtusa.jsm.dto.QOrder;
import com.virtusa.jsm.exception.DataNotFoundException;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

	public Bill getById(int id) throws DataNotFoundException {
		Optional<?> b;
		b=dao.findById(id);
		if(!b.isEmpty()) {
			 log.error(env.getProperty("displayBill1"));
			return dao.findById(id).get();}
		else {
			 log.error(env.getProperty("noBill"));
			throw new DataNotFoundException(env.getProperty("noBill"));}
	}

	public Object getByIdC(int id, String email) throws DataNotFoundException {
		Bill b;
		Customer c=cservice.getCByEmail(email);
		if(dao.existsById(id)) {
			b=dao.findById(id).get();
			if(b.getCust().equals(c)) {
				 log.error(env.getProperty("displayBill1"));
				return b;}
				 log.error(env.getProperty("noBill"));
				throw new DataNotFoundException(env.getProperty("noBill"));
			}
			log.error(env.getProperty("noBill"));
			throw new DataNotFoundException(env.getProperty("noBill"));
			}
	
}
