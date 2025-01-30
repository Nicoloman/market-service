package com.nqlo.ch.mkt.service.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class SaleDTO {
    
    @NotNull(message = "user is required")
    private Long user_id;

    @NotNull(message = "items is required")
        private List<SaleItemDTO> items;

    public List<SaleItemDTO> getItems() {
        return items;
    }

    public void setItems(List<SaleItemDTO> items) {
        this.items = items;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    
}
