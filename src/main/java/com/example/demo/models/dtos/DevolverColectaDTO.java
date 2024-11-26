package com.example.demo.models.dtos;

import java.util.List;

public class DevolverColectaDTO {

    private Long id;
    private String nombreRecolector;
    private String dniRecolector;
    private DevolverDepositoComunalDTO depositoComunal;
    private List<ItemRecolectadoDTO> items;
   
    

    public DevolverColectaDTO(Long id, String nombreRecolector, String dniRecolector,
			DevolverDepositoComunalDTO depositoComunal, List<ItemRecolectadoDTO> items) {
		super();
		this.id = id;
		this.nombreRecolector = nombreRecolector;
		this.dniRecolector = dniRecolector;
		this.depositoComunal = depositoComunal;
		this.items = items;
	}

	// Getters y setters
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

    public DevolverDepositoComunalDTO getDepositoComunal() {
        return depositoComunal;
    }

    public void setDepositoComunal(DevolverDepositoComunalDTO depositoComunal) {
        this.depositoComunal = depositoComunal;
    }

    public List<ItemRecolectadoDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemRecolectadoDTO> items) {
        this.items = items;
    }

    // DTO anidado para los ítems
    public static class ItemRecolectadoDTO {
        private Long id;
        private String nombre;
        private int cantidad;
        
        

        public ItemRecolectadoDTO(Long id, String nombre, int cantidad) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.cantidad = cantidad;
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
    }

    // DTO para el depósito comunal
    public static class DepositoComunalDTO {
        private Long id;
        private String nombre;

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
    }
}
