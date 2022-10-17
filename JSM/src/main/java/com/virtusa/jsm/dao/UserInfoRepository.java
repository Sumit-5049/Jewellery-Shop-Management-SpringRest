package com.virtusa.jsm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.jsm.dto.Customer;
import com.virtusa.jsm.dto.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer>{

	UserInfo findByC(Customer c);
	
}
