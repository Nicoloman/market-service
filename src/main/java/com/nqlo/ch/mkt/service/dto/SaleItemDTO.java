package com.nqlo.ch.mkt.service.dto;

import jakarta.validation.constraints.NotNull;

public class SaleItemDTO {

    @NotNull(message = "product is required")
    private Long product_id;
    @NotNull(message = "quantity is required")
    private int quantity;

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
