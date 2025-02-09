package com.nqlo.ch.mkt.service.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "categories")
public class Category {

    @Id // Primary Key, Autogenerated
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    
    @NotNull(message = "name is required")
    @Column(name= "name", nullable = false) // No Nulo
	private String name;

    @NotNull(message = "description is required")
    @Column(name= "description", nullable = false) //Unico y No Nulo
	private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

     // Me genera el createdAt para no tener que mandarlo en el JSON
    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }

    

    public Category(String name, String description) {
        this();
        this.description = description;
        this.name = name;
        this.created_at = LocalDateTime.now();
    }

    public Category() {
        super();
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

    public LocalDateTime getcreated_at() {
        return created_at;
    }

    public void setcreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
  
}