package com.nqlo.ch.mkt.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nqlo.ch.mkt.service.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
