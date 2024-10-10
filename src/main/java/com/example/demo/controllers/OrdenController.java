package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.demo.models.enums.Estado;
import com.example.demo.repository.ComunalDepositRepository;
import com.example.demo.repository.OrdenRepository;
import com.example.demo.repository.PrincipalDepositRepository;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/orden")
public class OrdenController {
    
    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired 
    private PrincipalDepositRepository principalDepositRepository;

    @Autowired
    private ComunalDepositRepository comunalDepositRepository;



   

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
        return new ResponseEntity<>(ordenAGuardar, HttpStatus.CREATED);
    }

    
    /**
     * @return una lista de objetos Orden que representan todas las órdenes.
     */
    @GetMapping
    public ResponseEntity<List<DevolverOrdenDTO>> obtenerOrdenes() {
        List<Orden> ordenes = ordenRepository.findAll();
        List<DevolverOrdenDTO> ordenesDTO = ordenes.stream()
                .map(orden -> new DevolverOrdenDTO(orden.getId(), orden.getPrincipalDeposit().getName(), orden.getEstado(), orden.getItems()))
                .collect(Collectors.toList());
        
        return new ResponseEntity<>(ordenesDTO, HttpStatus.OK);
    }
    

    /**
     * Reserva una orden cambiando su estado a RESERVADO y asignándole un depósito comunal.
     *
     * @param orderId El ID de la orden a reservar.
     * @return ResponseEntity con un mensaje de error si la orden no se encuentra o no está en estado PENDIENTE,
     *         o con un objeto DevolverOrdenDTO si la operación es exitosa.
     */
    @PutMapping("/reservar/{orderId}")
    public ResponseEntity<?> reservarOrden(@PathVariable Long orderId) {
        Optional<Orden> ordenOptional = ordenRepository.findById(orderId);
        if (!ordenOptional.isPresent()) {
            return ResponseEntity.badRequest().body("No se encontró la orden");
        }
        Orden orden = ordenOptional.get();

        if (orden.getEstado() != Estado.PENDIENTE) {
            return ResponseEntity.badRequest().body("La orden no está pendiente");
        }

        // Cuando haya auth, se debe obtener el deposito comunal del usuario autenticado. La linea siguiente es solo un ejemplo.
        ComunalDeposit comunalDeposit = comunalDepositRepository.findById(1L).get();
        
        orden.setComunalDeposit(comunalDeposit);
        orden.setEstado(Estado.RESERVADO);
        ordenRepository.save(orden);
        DevolverOrdenDTO ordenDTO = new DevolverOrdenDTO(orden.getId(), orden.getPrincipalDeposit().getName(), orden.getEstado(), orden.getItems());
        return new ResponseEntity<>(ordenDTO, HttpStatus.OK);
    }

    /**
     * Endpoint para marcar una orden como entregada.
     *
     * @param orderId El ID de la orden a entregar.
     * @return ResponseEntity con el estado de la operación. Si la orden no se encuentra,
     *         retorna un mensaje de error con código de estado 400. Si la orden no está
     *         en estado "RESERVADO", retorna un mensaje de error con código de estado 400.
     *         Si la operación es exitosa, retorna un objeto DevolverOrdenDTO con la información
     *         de la orden y código de estado 200.
     */
    @PutMapping("/entregar/{orderId}")
    public ResponseEntity<?> entregarOrden(@PathVariable Long orderId) {
        Optional<Orden> ordenOptional = ordenRepository.findById(orderId);
        if (!ordenOptional.isPresent()) {
            return ResponseEntity.badRequest().body("No se encontró la orden");
        }
        Orden orden = ordenOptional.get();

        if (orden.getEstado() != Estado.RESERVADO) {
            return ResponseEntity.badRequest().body("La orden no está reservada");
        }

        orden.setEstado(Estado.ENTREGADO);
        ordenRepository.save(orden);
        DevolverOrdenDTO ordenDTO = new DevolverOrdenDTO(orden.getId(), orden.getPrincipalDeposit().getName(), orden.getEstado(), orden.getItems());
        return new ResponseEntity<>(ordenDTO, HttpStatus.OK);
    }

}