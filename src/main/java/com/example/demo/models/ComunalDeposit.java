package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.enums.TipoUsuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity(name = "deposito_comunal")
public class ComunalDeposit extends User {


    @OneToMany(mappedBy = "comunalDeposit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Orden> orders;

    public ComunalDeposit() {
        super();
    }

    public ComunalDeposit(String fullName, String email, String password, TipoUsuario tipoUsuario) {
        super(fullName, email, password, tipoUsuario);
        this.orders = new ArrayList<>();
    }


    public List<Orden> getOrders() { return orders; }
    public void setOrders(List<Orden> orders) { this.orders = orders; }

}
