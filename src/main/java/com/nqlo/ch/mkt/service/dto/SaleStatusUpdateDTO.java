package com.nqlo.ch.mkt.service.dto;

import com.nqlo.ch.mkt.service.entities.SaleStatus;

public class SaleStatusUpdateDTO {
    private SaleStatus status;

    public SaleStatus getStatus() {
        return status;
    }

    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    public SaleStatusUpdateDTO(SaleStatus status) {
        this.status = status;
    }
}