package com.example.demo.models.dtos;

import java.util.List;

import com.example.demo.models.MaterialComunalDeposit;
import com.example.demo.models.Orden;

public class DevolverDepositoPrincipalDTO {

    private Long id;

    private String name;

    private List<Orden> orders;

    private List<MaterialComunalDeposit> materialDepositoComunalList;

    public DevolverDepositoPrincipalDTO(Long id, String name, List<Orden> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public DevolverDepositoPrincipalDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.orders = null;
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

    public List<MaterialComunalDeposit> getMaterialDepositoComunalList() {
        return materialDepositoComunalList;
    }

    public void setMaterialDepositoComunalList(List<MaterialComunalDeposit> materialDepositoComunalList) {
        this.materialDepositoComunalList = materialDepositoComunalList;
    }

    
}
