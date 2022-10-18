package com.virtusa.jsm.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.virtusa.jsm.dao.ReturnReqRepository;
import com.virtusa.jsm.dto.Bill;
import com.virtusa.jsm.dto.Customer;
import com.virtusa.jsm.dto.ReturnReq;
import com.virtusa.jsm.exception.DataNotFoundException;
import com.virtusa.jsm.exception.NotPossibleException;

@Service
public class ReturnReqService {
	
	@Autowired
	ReturnReqRepository dao;
	@Autowired
	BillService bservice;
	@Autowired
	CustomerService cservice;
	@Autowired
	BillService billservice;
	@Autowired
	Environment env;
	Log log= LogFactory.getLog(ReturnReqService.class);


//msg:Add new return request
//input: ReturnReq object , String customer_email
//output: String msg
	public Object addReturnReq(ReturnReq r, String email) throws DataNotFoundException, NotPossibleException {
		if(bservice.exitById(r.getBill().getBillid())) {
			LocalDateTime now = LocalDateTime.now();
	        Timestamp dateTime = Timestamp.valueOf(now);
	        int diff= dateTime.getDate()-r.getBill().getDateTime().getDate();
	        if(diff<4) {
	        	r.setDateTime(dateTime);
	        	r.setStatus("Pending");
	        	dao.save(r);
	        	log.info(env.getProperty("returnmsg"));
	        	return env.getProperty("returnmsg");
	        }
	        else
	        {
	        	log.error(env.getProperty("returnfail")+email);
	        	throw new NotPossibleException(env.getProperty("returnfail")+email);
	        }
		}
		log.error(env.getProperty("noBill"));
    	throw new DataNotFoundException(env.getProperty("noBill"));
	}

//msg:All ReturnReq for a customer
//input: String customer_email
//output: List of ReturnReq objects
	public Object getAllCus(String email) throws DataNotFoundException {
		List<ReturnReq> list=new ArrayList<>();
		Customer c=cservice.getCByEmail(email);
		list= (List<ReturnReq>) this.getAll();
		List<Bill> bills=(List<Bill>) billservice.getAllC(email);
		List<ReturnReq> rlist = new ArrayList<>() ;
		if(!list.isEmpty())
		{
			if(!bills.isEmpty()) 
			{
				boolean flag=false;
				for (Iterator<ReturnReq> iterator = list.iterator(); iterator.hasNext();) 
				{
					flag=false;
					ReturnReq a = (ReturnReq) iterator.next();
					for(int x=0;x<bills.size();x++) {
						Bill b=bills.get(x);
						if(a.getBill()==b) {
							flag=true;
							break;
						}
					}
					if(flag)
						rlist.add(a);
				}
			}	
			if(!rlist.isEmpty()) {
				 log.error(env.getProperty("displayAllReq"));
				return rlist;}
			else {
				 log.error(env.getProperty("noReturnReq"));
				throw new DataNotFoundException(env.getProperty("noReturnReq"));
			}
		}
	 	log.error(env.getProperty("noReturnReq"));
		throw new DataNotFoundException(env.getProperty("noReturnReq"));
	}

//msg:All ReturnReq 
//input: 
//output: List of ReturnReq objects
	public Object getAll() throws DataNotFoundException {
		List<ReturnReq> list=null;
		
		list= dao.findAll();
		if(!list.isEmpty()) {
			 log.error(env.getProperty("displayAllReq"));
			return list;}
		else {
			 log.error(env.getProperty("noReturnReq"));
			throw new DataNotFoundException(env.getProperty("noReturnReq"));}
	}

//msg:	Update ReturnReq by shop
//input: ReturnReq object
//output: String msg
	public Object updateReturnReq(ReturnReq r) {
		dao.save(r);
		log.info(env.getProperty("rupdated"));
		return env.getProperty("rupdated");
	}

//msg: Get a ReturnReq 
//input: int id
//output: ReturnReq object
	public Object getById(int id) throws DataNotFoundException{
		if(dao.existsById(id)) {
			 log.error(env.getProperty("displayReq1"));
			return dao.findById(id).get();}
		else {
			 log.error(env.getProperty("noReturnReq"));
			throw new DataNotFoundException(env.getProperty("noReturnReq"));}
	}

//msg: Get a ReturnReq for a customer
//input: int id, string customer_email
//output: ReturnReq object
	public Object getByIdCus(int id, String email) throws DataNotFoundException {
		ReturnReq r=(ReturnReq) this.getById(id);
		if(r.getBill().getCust().getEmail().equals(email)) {
			 log.error(env.getProperty("displayReq1")+email);
			return r;
		}
		else {
			 log.error(env.getProperty("noReturnReq")+email);
			throw new DataNotFoundException(env.getProperty("noReturnReq")+email);}
	}

//msg: Get all ReturnReq depending status
//input: string status
//output: List of ReturnReq objects
	public Object getAllByStatus(String status) throws DataNotFoundException {
		List<ReturnReq> list=null;
		list= dao.findAllByStatus(status);
		if(!list.isEmpty()) {
			 log.error(env.getProperty(status));
			return list;}
		else {
			 log.error(env.getProperty("no"+status));
			throw new DataNotFoundException("no"+env.getProperty("no"+status));}
	}

//msg: Get all ReturnReq depending on status for a customer
//input: string status, string customer_email
//output: List of ReturnReq objects
	public Object getAllByStatusCus(String email, String status) throws DataNotFoundException {
		List<ReturnReq> list=(List<ReturnReq>) this.getAllCus(email);
		List<ReturnReq> rlist = new ArrayList<>() ;
		for (Iterator<ReturnReq> iterator = list.iterator(); iterator.hasNext();) {
			ReturnReq a = (ReturnReq) iterator.next();
			if(a.getStatus().equals(status)) {
				rlist.add(a);
			}
		}		
		if(!rlist.isEmpty()) {
			 log.error(env.getProperty(status));
			return rlist;}
		else {
			 log.error(env.getProperty("no"+status)+email);
			throw new DataNotFoundException(env.getProperty("no"+status)+email);
			}
	}

//msg: Delete ReturnReq By a customer
//input: string email, ReturnReq Object
//output: String msg
	public Object deleteReturnReq(ReturnReq r, String email) throws DataNotFoundException {
		if(dao.existsById(r.getReturnid())) {
			if(r.getBill().getCust().getEmail().equals(email)) {
				int id=r.getReturnid();
				log.info(env.getProperty("reqDeleted")+id);
				dao.delete(r);
				return env.getProperty("reqDeleted")+id;
			}
				log.error(env.getProperty("noReturnReq"));
				throw new DataNotFoundException(env.getProperty("noReturnReq"));
		}
		log.error(env.getProperty("noReturnReq"));
		throw new DataNotFoundException(env.getProperty("noReturnReq"));
	}
	
}
