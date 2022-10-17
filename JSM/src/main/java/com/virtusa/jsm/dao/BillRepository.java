package com.virtusa.jsm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.virtusa.jsm.dto.Bill;
import com.virtusa.jsm.dto.Customer;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer>{

	List<Bill> findAllByCust(Customer c);

}
