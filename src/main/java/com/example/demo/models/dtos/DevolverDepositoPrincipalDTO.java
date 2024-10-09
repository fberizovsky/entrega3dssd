package com.example.demo.models.dtos;

import java.util.List;

import com.example.demo.models.Orden;

public class DevolverDepositoPrincipalDTO {

    private Long id;

    private String name;

    private List<Orden> orders;

    public DevolverDepositoPrincipalDTO(Long id, String name, List<Orden> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Orden> getOrders() {
        return orders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrders(List<Orden> orders) {
        this.orders = orders;
    }

    
}
