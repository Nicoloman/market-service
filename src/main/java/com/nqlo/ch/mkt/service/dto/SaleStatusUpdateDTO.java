package com.nqlo.ch.mkt.service.dto;

import com.nqlo.ch.mkt.service.entities.SaleStatus;

public class SaleStatusUpdateDTO {
    private SaleStatus status;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SaleStatus getStatus() {
        return status;
    }

    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    public SaleStatusUpdateDTO(SaleStatus status, String description) {
        this.status = status;
        this.description = description;
    }
}