package com.virtusa.jsm.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.jsm.dto.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Customer findByEmail(String email);

	boolean existsByEmail(String email);
	
	
	
//	@Query("Select c from Customer c where c.email=:p")
//	public Customer getCustomer(@Param("p") String email);
//
//	Customer findByEmail(String email);
}
