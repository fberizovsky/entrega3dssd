package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.enums.Material;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class PrincipalDeposit {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "principalDeposit", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Orden> orders;

    @OneToMany(mappedBy = "depositoPrincipal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialComunalDeposit> materialDepositoComunalList;



    // Constructor
    public PrincipalDeposit() {
        
    }

    public PrincipalDeposit(String name, String password) {
        this.name = name;
        this.password = password;
        this.orders = new ArrayList<>();
    }




    // Getters y setters
    public List<Orden> getOrders() { return orders; }
    public void setOrders(List<Orden> orders) { this.orders = orders; }

    public void addOrder(Orden order) {
        this.orders.add(order);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    

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
