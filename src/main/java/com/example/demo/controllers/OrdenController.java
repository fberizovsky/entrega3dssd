package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ComunalDeposit;
import com.example.demo.models.Orden;
import com.example.demo.models.PrincipalDeposit;
import com.example.demo.models.dtos.CrearOrdenDTO;
import com.example.demo.models.dtos.DevolverOrdenDTO;
import com.example.demo.repository.OrdenRepository;
import com.example.demo.repository.PrincipalDepositRepository;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/orden")
public class OrdenController {
    
    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired PrincipalDepositRepository principalDepositRepository;


   

    /**
     * Crea una nueva orden basada en los datos proporcionados en el DTO CrearOrdenDTO.
     *
     * @param crearOrdenDTO Objeto que contiene los datos necesarios para crear una nueva orden.
     * @return ResponseEntity con la orden creada si la operación es exitosa, o un mensaje de error si no se encuentra el depósito principal.
     */
    @PostMapping
    public ResponseEntity<?> crearOrden(@RequestBody CrearOrdenDTO crearOrdenDTO) {
        Optional<PrincipalDeposit> depositoPrincipalOptional = principalDepositRepository.findById(crearOrdenDTO.getPrincipalDepositId());
        if (!depositoPrincipalOptional.isPresent()) {
            return ResponseEntity.badRequest().body("No se encontró el deposito principal");
        }
        PrincipalDeposit depositoPrincipal = depositoPrincipalOptional.get();

        Orden orden = new Orden(crearOrdenDTO.getItems(), depositoPrincipal);
        Orden ordenAGuardar = ordenRepository.save(orden);
        return ResponseEntity.ok(ordenAGuardar);
    }

    
    /**
     * @return una lista de objetos Orden que representan todas las órdenes.
     */
    @GetMapping
    public List<DevolverOrdenDTO> obtenerOrdenes() {
        List<Orden> ordenes = ordenRepository.findAll();
        return ordenes.stream()
                .map(orden -> new DevolverOrdenDTO(orden.getId(), orden.getPrincipalDeposit().getName(), orden.getEstado(), orden.getItems()))
                .collect(Collectors.toList());
    }

    @PutMapping("/reservar/{orderId}")
    public ResponseEntity<?> reservarOrden(@PathVariable Long orderId, @RequestBody ComunalDeposit comunalDeposit) {
        // Orden orden = ordenRepository.findById(orderId).get();
        // orden.setComunalDeposit(comunalDeposit);
        // orden.setEstado(Estado.RESERVADO);
        // ordenRepository.save(orden);
        // return ResponseEntity.ok(orden);
        return ResponseEntity.badRequest().body("No implementado");
    }

    @PutMapping("/entregar/{orderId}")
    public ResponseEntity<?> entregarOrden(@PathVariable Long orderId) {
        // Orden orden = ordenRepository.findById(orderId).get();
        // if (orden.getEstado() != Estado.RESERVADO) {
        //     return ResponseEntity.badRequest().body("La orden no está reservada");
        // }
        // orden.setEstado(Estado.ENTREGADO);
        // ordenRepository.save(orden);
        // return ResponseEntity.ok(orden);
        return ResponseEntity.badRequest().body("No implementado");
    }

}