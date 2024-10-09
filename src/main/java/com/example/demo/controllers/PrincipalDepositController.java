package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.PrincipalDeposit;
import com.example.demo.models.dtos.CrearDepositoPrincipalDTO;
import com.example.demo.models.dtos.DevolverDepositoPrincipalDTO;
import com.example.demo.repository.PrincipalDepositRepository;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/principalDeposit")
public class PrincipalDepositController {
    
    @Autowired
    private PrincipalDepositRepository principalDepositRepository;


    
    /**
     * Método para obtener una lista de depósitos principales.
     * 
     * @return Una lista de objetos DevolverDepositoPrincipalDTO que representan los depósitos principales.
     */
    @GetMapping
    public List<DevolverDepositoPrincipalDTO> obtenerDepositosPrincipales() {
        List<PrincipalDeposit> depositosPrincipales = principalDepositRepository.findAll();
        return depositosPrincipales.stream()
                .map(depositoPrincipal -> new DevolverDepositoPrincipalDTO(
                        depositoPrincipal.getId(), 
                        depositoPrincipal.getName(), 
                        depositoPrincipal.getOrders()))
                .collect(Collectors.toList());
    }


    /**
     * Crea un nuevo depósito principal.
     *
     * @param depositoPrincipal El objeto PrincipalDeposit que contiene la información del depósito a crear.
     * @return El objeto PrincipalDeposit guardado en el repositorio.
     */
    @PostMapping
    public PrincipalDeposit crearDepositoPrincipal(@RequestBody CrearDepositoPrincipalDTO depositoPrincipalAInsertar) {
        PrincipalDeposit depositoPrincipal = new PrincipalDeposit(depositoPrincipalAInsertar.getName(), depositoPrincipalAInsertar.getPassword());
        return principalDepositRepository.save(depositoPrincipal);
    }
}
