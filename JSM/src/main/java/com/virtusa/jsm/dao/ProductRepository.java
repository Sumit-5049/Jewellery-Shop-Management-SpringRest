package com.virtusa.jsm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.jsm.dto.Bill;
import com.virtusa.jsm.dto.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	List<Product> findAllByMaterial(String material);

	List<Product> findAllByType(String type);

	
	
}
