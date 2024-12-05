package com.example.demo.controllers;

import com.example.demo.models.ItemRecolectado;
import com.example.demo.repository.ItemRecolectadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemRecolectadoController {

    @Autowired
    private ItemRecolectadoRepository itemRecolectadoRepository;

    /**
     * Actualiza la cantidad de un ItemRecolectado dado su ID.
     *
     * @param id       El ID del ItemRecolectado a actualizar.
     * @param cantidad La nueva cantidad para el ItemRecolectado.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PreAuthorize("hasAuthority('ROLE_DEPOSITO_COMUNAL')")
    @PutMapping("/modificar{id}")
    public ResponseEntity<?> actualizarCantidad(
            @PathVariable Long id,
            @RequestBody int cantidad
    ) {
        Optional<ItemRecolectado> optionalItem = itemRecolectadoRepository.findById(id);
    
        if (!optionalItem.isPresent()) {
            return ResponseEntity.badRequest().body("El ItemRecolectado con el ID proporcionado no existe.");
        }
    
        ItemRecolectado item = optionalItem.get();
        System.out.println("Cantidad recibida: " + cantidad); // Log de la cantidad
        item.setCantidad(cantidad);
        itemRecolectadoRepository.save(item);
        itemRecolectadoRepository.flush();
    
        // Confirmar si el item fue guardado correctamente
        System.out.println("Item actualizado: " + item); 
    
        return ResponseEntity.ok("Cantidad actualizada correctamente.");
    }
    
}
