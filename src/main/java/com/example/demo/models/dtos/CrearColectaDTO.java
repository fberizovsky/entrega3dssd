package com.example.demo.models.dtos;
import java.util.List;

public class CrearColectaDTO {

    private String nombreRecolector;
    private String dniRecolector;
    private Long idDepositoComunal; // ID del depósito comunal
    private List<ItemRecolectadoDTO> items; // Lista de ítems recolectados

    // Getters y setters
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

    public Long getIdDepositoComunal() {
        return idDepositoComunal;
    }

    public void setIdDepositoComunal(Long idDepositoComunal) {
        this.idDepositoComunal = idDepositoComunal;
    }

    public List<ItemRecolectadoDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemRecolectadoDTO> items) {
        this.items = items;
    }

    public static class ItemRecolectadoDTO {
        private String nombre;
        private int cantidad;

        // Getters y setters
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
    }
}
