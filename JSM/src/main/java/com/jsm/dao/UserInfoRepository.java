package com.jsm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsm.dto.Customer;
import com.jsm.dto.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer>{

	UserInfo findByC(Customer c);
	
}
