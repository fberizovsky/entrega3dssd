package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.enums.Material;
import com.example.demo.models.enums.TipoUsuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;


@Entity(name = "deposito_principal")
public class PrincipalDeposit extends User {

    
    @OneToMany(mappedBy = "principalDeposit", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Orden> orders;

    @OneToMany(mappedBy = "depositoPrincipal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialComunalDeposit> materialDepositoComunalList;

    // Constructor
    public PrincipalDeposit() {
        super();
    }

    public PrincipalDeposit(String fullName, String email, String password, TipoUsuario tipoUsuario) {
        super(fullName, email, password, tipoUsuario);
        this.orders = new ArrayList<>();
        this.materialDepositoComunalList = new ArrayList<>();
    }




    // Getters y setters
    public List<Orden> getOrders() { return orders; }
    public void setOrders(List<Orden> orders) { this.orders = orders; }

    public void addOrder(Orden order) {
        this.orders.add(order);
    }

    public List<MaterialComunalDeposit> getMaterialDepositoComunalList() {
        return materialDepositoComunalList;
    }
    public void setMaterialDepositoComunalList(List<MaterialComunalDeposit> materialDepositoComunalList) {
        this.materialDepositoComunalList = materialDepositoComunalList;
    }

    public void asignarMaterialADeposito(ComunalDeposit depositoComunal, Material material) {
        MaterialComunalDeposit relacion = new MaterialComunalDeposit();
        relacion.setComunalDeposit(depositoComunal);
        relacion.setMaterial(material);
        relacion.setDepositoPrincipal(this);
        materialDepositoComunalList.add(relacion);
    }
}
