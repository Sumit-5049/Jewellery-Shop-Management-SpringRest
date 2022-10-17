package com.virtusa.jsm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.virtusa.jsm.dto.Customer;
import com.virtusa.jsm.dto.ReturnReq;

@Repository
public interface ReturnReqRepository extends JpaRepository<ReturnReq, Integer>{
	
	public List<ReturnReq> findAllByStatus(String str);
}
