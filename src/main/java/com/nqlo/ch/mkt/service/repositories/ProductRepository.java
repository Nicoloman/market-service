package com.nqlo.ch.mkt.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nqlo.ch.mkt.service.entities.Product;

public interface  ProductRepository extends JpaRepository<Product, Long>{
    
}
