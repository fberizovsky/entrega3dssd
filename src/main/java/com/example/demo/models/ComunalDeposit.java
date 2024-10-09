package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ComunalDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "comunalDeposit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Orden> orders;

    public ComunalDeposit() {
    
    }

    public ComunalDeposit(String name, String password) {
        this.name = name;
        this.password = password;
        this.orders = new ArrayList<>();
    }

    // Getters y setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Orden> getOrders() { return orders; }
    public void setOrders(List<Orden> orders) { this.orders = orders; }

}
