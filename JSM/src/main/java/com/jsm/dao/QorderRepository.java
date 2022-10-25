package com.jsm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsm.dto.QOrder;

@Repository
public interface QorderRepository extends JpaRepository<QOrder, Integer>{

}
