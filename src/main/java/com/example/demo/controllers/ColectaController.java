package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.*;
import com.example.demo.models.dtos.*;
import com.example.demo.models.enums.EstadoColecta;
import com.example.demo.repository.*;
import com.example.demo.services.AuthenticationService;

@RestController
@RequestMapping("/api/colecta")
public class ColectaController {

    @Autowired
    private ColectaRepository colectaRepository;

    @Autowired
    private ComunalDepositRepository depositoComunalRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> crearColecta(@RequestBody CrearColectaDTO crearColectaDTO) {
        Optional<ComunalDeposit> depositoOptional = depositoComunalRepository
                .findById(crearColectaDTO.getIdDepositoComunal());
        if (!depositoOptional.isPresent()) {
            return ResponseEntity.badRequest().body("El depósito comunal no existe.");
        }

        ComunalDeposit depositoComunal = depositoOptional.get();

        // Crear la colecta
        Colecta colecta = new Colecta();
        colecta.setNombreRecolector(crearColectaDTO.getNombreRecolector());
        colecta.setDniRecolector(crearColectaDTO.getDniRecolector());
        colecta.setDepositoComunal(depositoComunal);

        // Crear los ítems recolectados
        List<ItemRecolectado> itemsRecolectados = crearColectaDTO.getItems().stream()
                .map(itemDTO -> new ItemRecolectado(itemDTO.getNombre(), itemDTO.getCantidad(), colecta))
                .collect(Collectors.toList());

        colecta.setItemsRecolectados(itemsRecolectados);

        // Guardar la colecta y devolver la respuesta
        Colecta colectaGuardada = colectaRepository.save(colecta);
        return new ResponseEntity<>(colectaGuardada, HttpStatus.CREATED);
    }

    /**
     * Devuelve una lista de colectas en formato DevolverColectaDTO.
     *
     * @return ResponseEntity con una lista de colectas.
     */
    @GetMapping
    public ResponseEntity<List<DevolverColectaDTO>> obtenerColectas() {
        List<Colecta> colectas = colectaRepository.findAll();
        List<DevolverColectaDTO> colectasDTO = colectas.stream()
                .map(colecta -> new DevolverColectaDTO(
                        colecta.getId(),
                        colecta.getNombreRecolector(),
                        colecta.getDniRecolector(),
                        // aca tengo que devolver un deposito comunal
                        colecta.getDepositoComunal().getId(),
                        colecta.getItemsRecolectados().stream()
                                .map(item -> new DevolverColectaDTO.ItemRecolectadoDTO(
                                        item.getId(),
                                        item.getNombre(),
                                        item.getCantidad()))
                                .collect(Collectors.toList()),
                        colecta.getEstado()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(colectasDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DEPOSITO_COMUNAL')")
    @GetMapping("/depositocomunal")
    public ResponseEntity<List<DevolverColectaDTO>> obtenerColectasDelDepositoComunal() {
        // Obtener el depósito comunal de la sesión
        ComunalDeposit comunalDeposit = (ComunalDeposit) authenticationService.getSessionUser();

        // Consultar las colectas asociadas al depósito comunal logueado
        List<Colecta> colectas = colectaRepository.findByDepositoComunal(comunalDeposit);

        // Transformar las colectas a DTO
        List<DevolverColectaDTO> colectasDTO = colectas.stream()
                .map(colecta -> new DevolverColectaDTO(
                        colecta.getId(),
                        colecta.getNombreRecolector(),
                        colecta.getDniRecolector(),
                        colecta.getDepositoComunal().getId(),
                        colecta.getItemsRecolectados().stream()
                                .map(item -> new DevolverColectaDTO.ItemRecolectadoDTO(
                                        item.getId(),
                                        item.getNombre(),
                                        item.getCantidad()))
                                .collect(Collectors.toList()),
                        colecta.getEstado()))
                .collect(Collectors.toList());

        // Devolver la lista de colectas
        return ResponseEntity.ok(colectasDTO);
    }

    @PreAuthorize("hasAuthority('ROLE_DEPOSITO_COMUNAL')")
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Colecta> rechazarColecta(@PathVariable Long id) {
        Colecta colecta = colectaRepository.findById(id).orElse(null);

        if (colecta == null) {
            return ResponseEntity.notFound().build();  // Si la colecta no existe
        }

        // Solo se puede rechazar si el estado actual es CREADO
        if (colecta.getEstado() == EstadoColecta.CREADO) {
            colecta.setEstado(EstadoColecta.RECHAZADO);  // Cambiar el estado a RECHAZADO
            colectaRepository.save(colecta);  // Guardar la colecta actualizada
            return ResponseEntity.ok(colecta);
        } else {
            return ResponseEntity.status(400).body(null);  // Si no está en estado CREADO, no se puede rechazar
        }
    }

    // Endpoint para aceptar la colecta
    @PreAuthorize("hasAuthority('ROLE_DEPOSITO_COMUNAL')")
    @PutMapping("/{id}/aceptar")
    public ResponseEntity<Colecta> aceptarColecta(@PathVariable Long id) {
        Colecta colecta = colectaRepository.findById(id).orElse(null);

        if (colecta == null) {
            return ResponseEntity.notFound().build();  // Si la colecta no existe
        }

        // Solo se puede aceptar si el estado actual es CREADO
        if (colecta.getEstado() == EstadoColecta.CREADO) {
            colecta.setEstado(EstadoColecta.ACEPTADO);  // Cambiar el estado a ACEPTADO
            colectaRepository.save(colecta);  // Guardar la colecta actualizada
            return ResponseEntity.ok(colecta);
        } else {
            return ResponseEntity.status(400).body(null);  // Si no está en estado CREADO, no se puede aceptar
        }
    }

}
