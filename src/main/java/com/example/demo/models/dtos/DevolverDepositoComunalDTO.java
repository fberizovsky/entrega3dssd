package com.example.demo.models.dtos;

import java.util.List;

public class DevolverDepositoComunalDTO {

    private Long id;

    private String name;

    private List<DevolverOrdenDTO> orders;


    public DevolverDepositoComunalDTO(Long id, String name, List<DevolverOrdenDTO> orders) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DevolverOrdenDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<DevolverOrdenDTO> orders) {
        this.orders = orders;
    }
}
