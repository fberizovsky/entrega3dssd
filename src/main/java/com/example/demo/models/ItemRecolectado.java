package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "item_recolectado")
public class ItemRecolectado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String nombre;

    private int cantidad;

    @ManyToOne(fetch = FetchType.LAZY) // Relación con Colecta
    @JsonIgnore // Para evitar ciclos de serialización
    private Colecta colecta;

    // Constructor vacío para JPA
    public ItemRecolectado() {}

    // Constructor para inicializar campos
    public ItemRecolectado(String nombre, int cantidad, Colecta colecta) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.colecta = colecta;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Colecta getColecta() {
        return colecta;
    }

    public void setColecta(Colecta colecta) {
        this.colecta = colecta;
    }
}
