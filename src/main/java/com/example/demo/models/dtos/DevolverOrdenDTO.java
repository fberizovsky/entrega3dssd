package com.example.demo.models.dtos;

import java.util.List;

import com.example.demo.models.Item;
import com.example.demo.models.enums.Estado;

public class DevolverOrdenDTO {
    
        private Long id;
    
        private String emitidaPor;
        
        private Estado estado;

        private List<Item> items;

        public DevolverOrdenDTO(Long id, String emitidaPor, Estado estado, List<Item> items) {
            this.id = id;
            this.emitidaPor = emitidaPor;
            this.estado = estado;
            this.items = items;
        }

        public Long getId() {
            return id;
        }

        public String getEmitidaPor() {
            return emitidaPor;
        }

        public Estado getEstado() {
            return estado;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setEmitidaPor(String emitidaPor) {
            this.emitidaPor = emitidaPor;
        }

        public void setEstado(Estado estado) {
            this.estado = estado;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        


    
}
