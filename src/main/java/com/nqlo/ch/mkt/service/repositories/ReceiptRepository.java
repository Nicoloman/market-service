package com.nqlo.ch.mkt.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nqlo.ch.mkt.service.entities.Receipt;
import com.nqlo.ch.mkt.service.entities.SaleStatus;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Receipt findBySaleId(Long saleId);
    Receipt findBySaleIdAndStatus(Long saleId, SaleStatus status);
}