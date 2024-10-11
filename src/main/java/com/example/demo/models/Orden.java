package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;
import com.example.demo.models.enums.Estado;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "orden")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne // Relación muchos a uno con orden
    @JsonIgnore
    private PrincipalDeposit principalDeposit;

    @OneToMany(mappedBy = "orden", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Item> items;
    
    private Estado estado;

    @ManyToOne
    private ComunalDeposit comunalDeposit;

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public Orden(PrincipalDeposit principalDeposit) {
        this.items = new ArrayList<>();
        this.principalDeposit = principalDeposit;
    }

    public Orden(List<Item> items, PrincipalDeposit principalDeposit) {
        this.estado = Estado.PENDIENTE;
        this.principalDeposit = principalDeposit;
        this.items = new ArrayList<>(); // Inicializar la lista
    
        // Agregar items a la orden
        for (Item item : items) {
            addItem(item);
        }
    }
    


    public Orden() {
        this.items = new ArrayList<>();
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public PrincipalDeposit getPrincipalDeposit() { return principalDeposit; }
    public void setPrincipalDeposit(PrincipalDeposit principalDeposit) { this.principalDeposit = principalDeposit; }

    public ComunalDeposit getComunalDeposit() { return comunalDeposit; }
    public void setComunalDeposit(ComunalDeposit comunalDeposit) { this.comunalDeposit = comunalDeposit; }

    // Método para agregar un item a la colecta
    public void addItem(Item item) {
        item.setOrden(this); // Esto establece la relación bidireccional
        items.add(item);
    }
}
