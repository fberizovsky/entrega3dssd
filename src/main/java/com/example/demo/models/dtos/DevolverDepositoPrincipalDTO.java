package com.example.demo.models.dtos;

import java.util.List;

import com.example.demo.models.MaterialComunalDeposit;

public class DevolverDepositoPrincipalDTO {

    private Long id;

    private String name;

    private List<DevolverOrdenDTO> orders;

    private List<MaterialComunalDeposit> materialDepositoComunalList;

    public DevolverDepositoPrincipalDTO(Long id, String name, List<DevolverOrdenDTO> orders) {
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

    public List<DevolverOrdenDTO> getOrders() {
        return orders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrders(List<DevolverOrdenDTO> orders) {
        this.orders = orders;
    }

    public List<MaterialComunalDeposit> getMaterialDepositoComunalList() {
        return materialDepositoComunalList;
    }

    public void setMaterialDepositoComunalList(List<MaterialComunalDeposit> materialDepositoComunalList) {
        this.materialDepositoComunalList = materialDepositoComunalList;
    }

    
}
