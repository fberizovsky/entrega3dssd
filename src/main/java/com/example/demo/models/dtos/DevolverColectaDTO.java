package com.example.demo.models.dtos;

import java.lang.management.LockInfo;
import java.util.List;

import com.example.demo.models.enums.EstadoColecta;

public class DevolverColectaDTO {

    private Long id;
    private String nombreRecolector;
    private String dniRecolector;
    private Long idDepositoComunal;
    private EstadoColecta estado;
    private List<ItemRecolectadoDTO> items;
   
    

    public DevolverColectaDTO(Long id, String nombreRecolector, String dniRecolector,
			Long depositoComunal, List<ItemRecolectadoDTO> items, EstadoColecta estado) {
		super();
		this.id = id;
		this.nombreRecolector = nombreRecolector;
		this.dniRecolector = dniRecolector;
		this.idDepositoComunal = depositoComunal;
		this.items = items;
        this.estado = estado;
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

    public Long getidDepositoComunal() {
        return idDepositoComunal;
    }

    public void setDepositoComunal(Long depositoComunal) {
        this.idDepositoComunal = depositoComunal;
    }

    public List<ItemRecolectadoDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemRecolectadoDTO> items) {
        this.items = items;
    }

    public void setEstado(EstadoColecta estado){
        this.estado = estado;
    }

    public EstadoColecta getEstado() {
        return estado;
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
