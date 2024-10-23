package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ComunalDeposit;
import com.example.demo.models.MaterialComunalDeposit;
import com.example.demo.models.PrincipalDeposit;
import com.example.demo.models.dtos.CrearMaterialComunalDepositDTO;
import com.example.demo.models.dtos.DevolverDepositoComunalDTO;
import com.example.demo.models.dtos.DevolverDepositoPrincipalDTO;
import com.example.demo.models.dtos.DevolverOrdenDTO;
import com.example.demo.models.enums.Material;
import com.example.demo.repository.ComunalDepositRepository;
import com.example.demo.repository.MaterialComunalDepositRepository;
import com.example.demo.repository.PrincipalDepositRepository;
import com.example.demo.services.AuthenticationService;
import com.example.demo.validators.MaterialValidator;

import jakarta.persistence.EntityNotFoundException;

import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/principalDeposit")
public class PrincipalDepositController {
    
    @Autowired
    private PrincipalDepositRepository principalDepositRepository;

    @Autowired
    private ComunalDepositRepository comunalDepositRepository;

    @Autowired 
    private MaterialComunalDepositRepository materialComunalDepositRepository;

    @Autowired
    private AuthenticationService authenticationService;

    
    /**
     * Método para obtener una lista de depósitos principales.
     * 
     * @return Una ResponseEntity que contiene una lista de objetos DevolverDepositoPrincipalDTO que representan los depósitos principales.
     */
    @GetMapping
    public ResponseEntity<List<DevolverDepositoPrincipalDTO>> obtenerDepositosPrincipales() {
        List<PrincipalDeposit> depositosPrincipales = principalDepositRepository.findAll();
        List<DevolverDepositoPrincipalDTO> responseDTOs = depositosPrincipales.stream()
                .map(depositoPrincipal -> new DevolverDepositoPrincipalDTO(
                        depositoPrincipal.getId(), 
                        depositoPrincipal.getFullName(), 
                        depositoPrincipal.getOrders().stream().map(order -> new DevolverOrdenDTO(order.getId(), order.getPrincipalDeposit().getFullName(), order.getEstado(), order.getItems())).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DEPOSITO_PRINCIPAL')")
    @PostMapping("/asignarMaterial")
    public ResponseEntity<?> asignarMaterial(@RequestBody CrearMaterialComunalDepositDTO request) {
        
        PrincipalDeposit depositoPrincipal =  (PrincipalDeposit) authenticationService.getSessionUser();

        if (!MaterialValidator.esMaterialValido(request.getMaterial())) {
            return ResponseEntity.badRequest().body("El material no es válido");
        }
        Material material = Material.valueOf(request.getMaterial().toUpperCase());

        Optional<ComunalDeposit> depositoComunalOptional = comunalDepositRepository.findById(request.getComunalDepositId());
        if (!depositoComunalOptional.isPresent()) {
            return ResponseEntity.badRequest().body("No se encontró el depósito comunal");
        }

        List<MaterialComunalDeposit> materialComunalDepositOptional = materialComunalDepositRepository.findByMaterialAndComunalDepositId(material, depositoComunalOptional.get().getId());

        if (!materialComunalDepositOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("El material ya está asignado a este depósito comunal");
        }

        ComunalDeposit depositoComunal = depositoComunalOptional.get();
        depositoPrincipal.asignarMaterialADeposito(depositoComunal, material);

        
        depositoPrincipal = principalDepositRepository.save(depositoPrincipal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_DEPOSITO_PRINCIPAL')")
    @GetMapping("/materiales")
    public ResponseEntity<?> obtenerDepositosComunalesPorMaterial(@RequestParam Material material) {

        PrincipalDeposit depositoPrincipal =  (PrincipalDeposit) authenticationService.getSessionUser();
        List<MaterialComunalDeposit> depositos_comunales_list= materialComunalDepositRepository.findByMaterialAndDepositoPrincipal(material, depositoPrincipal);


        List<DevolverDepositoComunalDTO> responseDTOs = depositos_comunales_list.stream()
                .map(materialComunalDeposit -> new DevolverDepositoComunalDTO(
                        materialComunalDeposit.getComunalDeposit().getId(), 
                        materialComunalDeposit.getComunalDeposit().getFullName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
        
        /*List<Optional<ComunalDeposit>> depositosComunales = depositosComunalesIds.stream()
            .map(id -> comunalDepositRepository.findById(id))
            .collect(Collectors.toList());

        System.out.println(depositosComunales);

        /*List<DevolverDepositoComunalDTO> responseDTOs = depositosComunales.stream()
                .map(ComunalDeposit -> new DevolverDepositoComunalDTO(
                    ComunalDeposit.getId(), 
                    ComunalDeposit.getFullName()))
                .collect(Collectors.toList()); 

        /*List<DevolverDepositoComunalDTO> responseDTOs = depositosComunalesIds.stream()
                .map(deposito -> new DevolverDepositoComunalDTO(
                    deposito.getId(), 
                    deposito.getFullName()))
                .collect(Collectors.toList());*/

        //return new ResponseEntity<>(depositosComunales, HttpStatus.OK);
        //return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }
}
