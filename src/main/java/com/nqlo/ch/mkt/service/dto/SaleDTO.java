package com.nqlo.ch.mkt.service.dto;

import java.time.LocalDateTime;

import com.nqlo.ch.mkt.service.entities.Product;
import com.nqlo.ch.mkt.service.entities.User;

public class SaleDTO {

    private Long id;
    private User user;
    private Product product;
    private int quantity;
    private Long Data;
    private LocalDateTime created_at;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Long getData() {
        return Data;
    }
    public void setData(Long data) {
        Data = data;
    }
    
}
