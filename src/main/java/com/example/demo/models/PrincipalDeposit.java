package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

public class PrincipalDeposit extends User {

    @OneToMany(mappedBy = "orden", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Orden> orders;



    // Constructor
    public PrincipalDeposit() {
        super();
    }

    public PrincipalDeposit(String name, String password) {
        super(name, password);
        this.orders = new ArrayList<>();
    }




    // Getters y setters
    public List<Orden> getOrders() { return orders; }
    public void setOrders(List<Orden> orders) { this.orders = orders; }

    public void addOrder(Orden order) {
        this.orders.add(order);
    }
    
}
