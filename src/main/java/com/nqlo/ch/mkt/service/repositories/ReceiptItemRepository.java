package com.nqlo.ch.mkt.service.repositories;

import com.nqlo.ch.mkt.service.entities.ReceiptItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, Long> {
}