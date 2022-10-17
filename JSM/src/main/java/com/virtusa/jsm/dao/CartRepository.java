package com.virtusa.jsm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.jsm.dto.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

}
