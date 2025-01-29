package com.nqlo.ch.mkt.service.dto;

import jakarta.validation.constraints.NotNull;

public class ProductDTO {

    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "description is required")
    private String description;

    @NotNull(message = "category is required")
    private Long categoryId;
    @NotNull(message = "price is required")
    private Long price;
    @NotNull(message = "stock is required")
    private int stock;
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
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
