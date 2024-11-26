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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.*;
import com.example.demo.models.dtos.*;
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

    /**
     * Crea una nueva colecta basada en los datos proporcionados en el DTO CrearColectaDTO.
     *
     * @param crearColectaDTO Objeto que contiene los datos necesarios para crear una colecta.
     * @return ResponseEntity con la colecta creada o un mensaje de error si no se encuentra el depósito comunal.
     */
    @PostMapping
    public ResponseEntity<?> crearColecta(@RequestBody CrearColectaDTO crearColectaDTO) {
        Optional<ComunalDeposit> depositoOptional = depositoComunalRepository.findById(crearColectaDTO.getIdDepositoComunal());
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
                .map(itemDTO -> new ItemRecolectado(itemDTO.getNombre(),itemDTO.getCantidad(),colecta))
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
                        //aca tengo que devolver un deposito comunal
                        new DevolverDepositoComunalDTO(
                        		colecta.getDepositoComunal().getId(),
                        		colecta.getDepositoComunal().getFullName(),
                        		colecta.getDepositoComunal().getOrders().stream()
                        			.map(orden -> new DevolverOrdenDTO(orden.getId(), orden.getPrincipalDeposit().getFullName(), orden.getEstado(), orden.getItems()))
                        			.collect(Collectors.toList())
                        		),
                        colecta.getItemsRecolectados().stream()
                                .map(item -> new DevolverColectaDTO.ItemRecolectadoDTO(
                                        item.getId(),
                                        item.getNombre(),
                                        item.getCantidad()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return new ResponseEntity<>(colectasDTO, HttpStatus.OK);
    }
}
