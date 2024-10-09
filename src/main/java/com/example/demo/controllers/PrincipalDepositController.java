package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.PrincipalDeposit;
import com.example.demo.repository.PrincipalDepositRepository;

@RestController
@RequestMapping("/api/principalDeposit")
public class PrincipalDepositController {
    
    @Autowired
    private PrincipalDepositRepository principalDepositRepository;


    @GetMapping
    public List<PrincipalDeposit> obtenerDepositosPrincipales() {
        List<PrincipalDeposit> depositosPrincipales = principalDepositRepository.findAll();
        return depositosPrincipales;
    }

    /* Se le puede mandar esto
    {
        "name": "Nombre",
        "password": "contraseñaSegura"
    }

     */
    @PostMapping
    public PrincipalDeposit crearDepositoPrincipal(@RequestBody PrincipalDeposit depositoPrincipal) {
        return principalDepositRepository.save(depositoPrincipal);
    }
}
