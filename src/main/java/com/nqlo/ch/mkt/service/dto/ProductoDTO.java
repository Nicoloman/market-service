package com.nqlo.ch.mkt.service.dto;

import java.time.LocalDateTime;

import com.nqlo.ch.mkt.service.entities.Category;

public class ProductoDTO {
    
    private Long id;
    private String name;
    private String description;
    private Category category;
    private Long price;
    private int stock;
    private LocalDateTime created_at;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

}
