package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ComunalDeposit;
import com.example.demo.models.dtos.DevolverDepositoComunalDTO;
import com.example.demo.models.dtos.DevolverOrdenDTO;
import com.example.demo.repository.ComunalDepositRepository;

@RestController
@RequestMapping("/api/comunalDeposit")
public class ComunalDepositController {
    

    @Autowired 
    private ComunalDepositRepository comunalDepositRepository;


    /**
     * Método para obtener una lista de depósitos comunales.
     * 
     * @return Una ResponseEntity que contiene una lista de objetos DevolverDepositoComunalDTO que representan los depósitos comunales.
     */
    @PreAuthorize("hasAuthority('ROLE_DEPOSITO_PRINCIPAL')")
    @GetMapping
    public ResponseEntity<List<DevolverDepositoComunalDTO>> obtenerDepositosPrincipales() {
        List<ComunalDeposit> depositosComunales = comunalDepositRepository.findAll();
        List<DevolverDepositoComunalDTO> responseDtoList = depositosComunales.stream()
                .map(depositoComunal -> new DevolverDepositoComunalDTO(
                        depositoComunal.getId(), 
                        depositoComunal.getFullName(),
                        depositoComunal.getOrders().stream()
                            .map(orden -> new DevolverOrdenDTO(orden.getId(), orden.getPrincipalDeposit().getFullName(), orden.getEstado(), orden.getItems()))
                            .collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

}
