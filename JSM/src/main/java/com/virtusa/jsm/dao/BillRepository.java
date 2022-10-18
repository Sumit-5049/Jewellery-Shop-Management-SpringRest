package com.virtusa.jsm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.jsm.dto.Bill;
import com.virtusa.jsm.dto.Customer;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer>{

	List<Bill> findAllByCust(Customer c);

}
