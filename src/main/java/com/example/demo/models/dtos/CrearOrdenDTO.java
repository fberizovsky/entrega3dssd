package com.example.demo.models.dtos;

import java.util.List;

import com.example.demo.models.Item;
import com.example.demo.models.enums.Estado;

public class CrearOrdenDTO {

    private Long principalDepositId;

    private List<Item> items;

    private Estado estado;



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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
}
