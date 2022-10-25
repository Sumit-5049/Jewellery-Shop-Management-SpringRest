package com.jsm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsm.dto.Shop;

@Repository
public interface ShopRepository  extends JpaRepository<Shop, Integer>{

	Shop findByEmail(String email);

}
