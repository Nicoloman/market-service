package com.nqlo.ch.mkt.service.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nqlo.ch.mkt.service.entities.Receipt;
import com.nqlo.ch.mkt.service.entities.Sale;
import com.nqlo.ch.mkt.service.exceptions.ResourceNotFoundException;
import com.nqlo.ch.mkt.service.repositories.ReceiptRepository;
import com.nqlo.ch.mkt.service.repositories.SaleRepository;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Receipt> getReceipts() {
        return receiptRepository.findAll();
    }

    public Receipt findById(Long id) {
        return receiptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receipt with id: " + id + " couldn't be found"));
    }

    public Receipt save(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale with id: " + saleId + " couldn't be found"));


        // Check if a receipt already exists for this sale and status
        Receipt existingReceipt = receiptRepository.findBySaleAndSaleStatus(sale, sale.getStatus());
        if (existingReceipt != null) {
            return existingReceipt;
        }

        Receipt receipt = new Receipt();
        receipt.setSale(sale);
        receipt.setUserName(sale.getUser().getName());
        receipt.setStatus(sale.getStatus());

        // Obtener la fecha actual desde el servicio REST
        try {
            String dateUrl = "http://worldclockapi.com/api/json/utc/now";
            Map<String, Object> response = restTemplate.getForObject(dateUrl, Map.class);
            String currentDateStr = (String) response.get("currentDateTime");
            Date currentDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").parse(currentDateStr);
            receipt.setDate(currentDate);
        } catch (Exception e) {
            // Usar la fecha actual como fallback si el servicio no está disponible
            receipt.setDate(new Date());
        }

        return receiptRepository.save(receipt);
    }
}
