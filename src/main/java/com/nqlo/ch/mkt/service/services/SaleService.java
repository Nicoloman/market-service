package com.nqlo.ch.mkt.service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqlo.ch.mkt.service.entities.Sale;
import com.nqlo.ch.mkt.service.entities.SaleStatus;
import com.nqlo.ch.mkt.service.exceptions.ResourceNotFoundException;
import com.nqlo.ch.mkt.service.repositories.ProductRepository;
import com.nqlo.ch.mkt.service.repositories.SaleRepository;
import com.nqlo.ch.mkt.service.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

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
        if (!saleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sale with id: " + id + " couldnt be found");
        }
        saleRepository.deleteById(id);
    }

    //Assign category to Sale
}
