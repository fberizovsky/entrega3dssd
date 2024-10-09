package com.example.demo.models.dtos;

import java.util.List;

import com.example.demo.models.Item;

public class CrearOrdenDTO {

    private Long principalDepositId;

    private List<Item> items;

    public Long getPrincipalDepositId() {
        return principalDepositId;
    }

    public void setPrincipalDepositId(Long principalDepositId) {
        this.principalDepositId = principalDepositId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    
    
}
