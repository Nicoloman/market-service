package com.nqlo.ch.mkt.service.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "name is required")
    private String name;

    
    @NotNull(message = "email is required")
    @Email(message = "email should be valid")
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull(message = "password is required")
    @Size(min = 8, message = "Password should be at least 8 chars long")
    private String password;

    @NotNull(message = "role is required")
    private String role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "user")
    @JsonBackReference // Para evitar la serialización infinita del lado "User"
    private List<Sale> sales;

    // Me genera el createdAt para no tener que mandarlo en el JSON
    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }

    // Constructor vacío
    public User() {
    }

    // Constructor completo
    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.created_at = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getcreated_at() {
        return created_at;
    }

    public void setcreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", email=").append(email);
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }

}
