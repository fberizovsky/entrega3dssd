package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.enums.EstadoColecta;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "colecta")
public class Colecta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String nombreRecolector; // Aquí agregamos el campo para el nombre del recolector
    private String dniRecolector; // Aquí agregamos el campo para el DNI del recolector

    @OneToMany(mappedBy = "colecta", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemRecolectado> itemsRecolectados;

    @ManyToOne(fetch = FetchType.LAZY) // Relación inversa: muchas colectas pueden compartir un depósito comunal
    @JsonIgnore
    private ComunalDeposit depositoComunal;

    private EstadoColecta estado = EstadoColecta.CREADO; // Valor por defecto es CREADO

    public Colecta() {
        this.itemsRecolectados = new ArrayList<>();
    }

    public Colecta(String nombreRecolector, String dniRecolector, ComunalDeposit depositoComunal) {
        this.nombreRecolector = nombreRecolector;
        this.dniRecolector = dniRecolector;
        this.depositoComunal = depositoComunal;
        this.itemsRecolectados = new ArrayList<>();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreRecolector() {
        return nombreRecolector;
    }

    public void setNombreRecolector(String nombreRecolector) {
        this.nombreRecolector = nombreRecolector;
    }

    public String getDniRecolector() {
        return dniRecolector;
    }

    public void setDniRecolector(String dniRecolector) {
        this.dniRecolector = dniRecolector;
    }

    public List<ItemRecolectado> getItemsRecolectados() {
        return itemsRecolectados;
    }

    public void setItemsRecolectados(List<ItemRecolectado> itemsRecolectados) {
        this.itemsRecolectados = itemsRecolectados;
    }

    public ComunalDeposit getDepositoComunal() {
        return depositoComunal;
    }

    public void setDepositoComunal(ComunalDeposit depositoComunal) {
        this.depositoComunal = depositoComunal;
    }

    // Métodos adicionales

    public void addItemRecolectado(ItemRecolectado item) {
        item.setColecta(this); // Establecer relación bidireccional
        this.itemsRecolectados.add(item);
    }

    public void removeItemRecolectado(ItemRecolectado item) {
        item.setColecta(null); // Eliminar relación bidireccional
        this.itemsRecolectados.remove(item);
    }

    public boolean containsItemRecolectado(ItemRecolectado item) {
        return this.itemsRecolectados.contains(item);
    }

    public void clearItemsRecolectados() {
        for (ItemRecolectado item : this.itemsRecolectados) {
            item.setColecta(null); // Eliminar relación bidireccional
        }
        this.itemsRecolectados.clear();
    }

    // Getters y Setters
    public EstadoColecta getEstado() {
        return estado;
    }

    public void setEstado(EstadoColecta estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Colecta{" +
                "id=" + id +
                ", nombreRecolector='" + nombreRecolector + '\'' +
                ", dniRecolector='" + dniRecolector + '\'' +
                ", itemsRecolectados=" + itemsRecolectados.size() +
                ", depositoComunal=" + (depositoComunal != null ? depositoComunal.getId() : "null") +
                '}';
    }
}
