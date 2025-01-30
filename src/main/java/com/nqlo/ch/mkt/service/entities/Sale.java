package com.nqlo.ch.mkt.service.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "user is required")
    private User user;

    @OneToMany(mappedBy = "sale", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<SaleItem> items = new ArrayList<>();

    public List<SaleItem> getItems() {
        return items;
    }

    @Enumerated(EnumType.STRING)
    @NotNull(message = "status is required")
    private SaleStatus status;

    @NotNull
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }

    @NotNull(message = "total is required")
    private Long total;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @JsonProperty("user_id")
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    public Sale() {
    }

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }

    public Sale(User user, List<SaleItem> items, String description) {
        this.user = user;
        this.items = items;
        this.description = description;
        this.total = items.stream().mapToLong(SaleItem::getTotal).sum();
        this.created_at = LocalDateTime.now();
        this.status = SaleStatus.SOLD; // Default status
    }

    // Getters y Setters
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

    public SaleStatus getStatus() {
        return status;
    }

    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public LocalDateTime getcreated_at() {
        return created_at;
    }

    public void setcreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
