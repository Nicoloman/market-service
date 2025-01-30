package com.nqlo.ch.mkt.service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqlo.ch.mkt.service.entities.Receipt;
import com.nqlo.ch.mkt.service.entities.Sale;
import com.nqlo.ch.mkt.service.entities.SaleStatus;
import com.nqlo.ch.mkt.service.exceptions.ResourceNotFoundException;
import com.nqlo.ch.mkt.service.repositories.SaleRepository;

import jakarta.transaction.Transactional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ReceiptService receiptService;


    public List<Sale> getSales() {
        return saleRepository.findAll();
    }

    public Sale findById(Long id) {
        return saleRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Sale with id: " + id + " couldnt be found"));
    }

    @Transactional
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    @Transactional
    public Sale updateStatusById(Long id, SaleStatus newStatus, String description) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale with id: " + id + " couldn't be found"));

        sale.setStatus(newStatus);
        sale.setDescription(description);
        return saleRepository.save(sale);
    }

    @Transactional
    public void deleteById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale with id: " + id + " couldn't be found"));

        // Antes de eliminar me fijo si existe un compronate asociado a la venta
        Receipt receipt = receiptService.findBySaleId(sale.getId());
        if (receipt == null) {
            // Si no existe, lo genero y continuo con lo solicitado
            receiptService.save(id);
        }

        saleRepository.deleteById(id);
    }
}
