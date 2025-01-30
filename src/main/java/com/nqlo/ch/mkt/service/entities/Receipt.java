package com.nqlo.ch.mkt.service.entities;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long saleId; // Almacenar el ID de la venta

    @NotNull(message = "user is required")
    private String userName;

    @NotNull(message = "Date is required")
    private Date date;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "status is required")
    private SaleStatus status;
    
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ReceiptItem> items;
    
    private Long totalAmount;
    
    public Long getSaleId() {
        return saleId;
    }
    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }
    public List<ReceiptItem> getItems() {
        return items;
    }
    public void setItems(List<ReceiptItem> items) {
        this.items = items;
    }
    public Long getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public SaleStatus getStatus() {
        return status;
    }
    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    // Getters and setters
}