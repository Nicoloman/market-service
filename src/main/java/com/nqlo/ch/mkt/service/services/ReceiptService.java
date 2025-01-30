package com.nqlo.ch.mkt.service.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nqlo.ch.mkt.service.entities.Receipt;
import com.nqlo.ch.mkt.service.entities.ReceiptItem;
import com.nqlo.ch.mkt.service.entities.Sale;
import com.nqlo.ch.mkt.service.entities.SaleItem;
import com.nqlo.ch.mkt.service.entities.SaleStatus;
import com.nqlo.ch.mkt.service.exceptions.ResourceNotFoundException;
import com.nqlo.ch.mkt.service.repositories.ReceiptItemRepository;
import com.nqlo.ch.mkt.service.repositories.ReceiptRepository;
import com.nqlo.ch.mkt.service.repositories.SaleRepository;

import jakarta.transaction.Transactional;

@Service
public class ReceiptService {

    public static final String dateUrl = "http://worldclockapi.com/api/json/utc/now";
    
    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptItemRepository receiptItemRepository;

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

    public Receipt findBySaleId(Long saleId){
        return receiptRepository.findBySaleId(saleId);
    }

    @Transactional
public Receipt save(Long saleId) {
    Sale sale = saleRepository.findById(saleId)
            .orElseThrow(() -> new ResourceNotFoundException("Sale with id: " + saleId + " couldn't be found"));

    // Chequear si ya existe un recibo con el mismo ID de venta y estado
    Receipt existingReceipt = receiptRepository.findBySaleIdAndStatus(saleId, sale.getStatus());
    if (existingReceipt != null) {
        return existingReceipt;
    }

    Receipt receipt = new Receipt();
    receipt.setSaleId(sale.getId());
    receipt.setUserName(sale.getUser().getName());
    receipt.setStatus(sale.getStatus());
    receipt.setTotalAmount(sale.getTotal());

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

    // Guardar el recibo primero
    receipt = receiptRepository.save(receipt);

    List<ReceiptItem> receiptItems = new ArrayList<>();
    for (SaleItem saleItem : sale.getItems()) {
        ReceiptItem receiptItem = new ReceiptItem();
        receiptItem.setReceipt(receipt);
        receiptItem.setProductId(saleItem.getProductId());
        receiptItem.setProductName(saleItem.getProductName());
        receiptItem.setQuantity(saleItem.getQuantity());
        receiptItem.setPrice(saleItem.getPrice());
        receiptItems.add(receiptItem);
    }

    // Guardar los items del recibo
    receiptItemRepository.saveAll(receiptItems);

    // Asignar los items al recibo
    receipt.setItems(receiptItems);

    return receipt;
}
}
