package com.nqlo.ch.mkt.service.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Entity //JPA Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private int stock;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

        @OneToMany(mappedBy = "product")
    @JsonBackReference  // Evitar la serialización infinita
    private List<Sale> sales;


     // Me genera el createdAt para no tener que mandarlo en el JSON
    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }

    // Constructor vacío
    public Product() {}

    // Constructor completo
    public Product(String name, String description, Category category, Long price, int stock) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.created_at = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public LocalDateTime getcreated_at() {
        return created_at;
    }

    public void setcreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
    
}