package com.nqlo.ch.mkt.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nqlo.ch.mkt.service.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    
}
