package com.nqlo.ch.mkt.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "sale_details")
public class SaleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long price;

    // Constructor vacío
    public SaleDetail() {}

    // Constructor completo
    public SaleDetail(Sale sale, Product product, Long quantity, Long price) {
        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}