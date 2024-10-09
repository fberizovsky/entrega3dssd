package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ComunalDeposit;
import com.example.demo.models.dtos.CrearDepositoComunalDTO;
import com.example.demo.models.dtos.DevolverDepositoComunalDTO;
import com.example.demo.repository.ComunalDepositRepository;

@RestController
@RequestMapping("/api/comunalDeposit")
public class ComunalDepositController {
    

    @Autowired 
    private ComunalDepositRepository comunalDepositRepository;


    /**
     * Método para obtener una lista de depósitos comunales.
     * 
     * @return Una lista de objetos DevolverDepositoCoomunalDTO que representan los depósitos comunales.
     */
    @GetMapping
    public List<DevolverDepositoComunalDTO> obtenerDepositosPrincipales() {
        List<ComunalDeposit> depositosComunales = comunalDepositRepository.findAll();
        return depositosComunales.stream()
                .map(depositoComunal -> new DevolverDepositoComunalDTO(
                        depositoComunal.getId(), 
                        depositoComunal.getName()))
                .collect(Collectors.toList());
    }


    /**
     * Crea un nuevo depósito comunal.
     *
     * @param CrearDepositoComunalDTO El objeto ComunalDeposit que contiene la información del depósito a crear.
     * @return El objeto ComunalDeposit guardado en el repositorio.
     */
    @PostMapping
    public ComunalDeposit crearDepositoComunal(@RequestBody CrearDepositoComunalDTO depositoComunalAInsertar) {
        ComunalDeposit depositoComunal = new ComunalDeposit(depositoComunalAInsertar.getName(), depositoComunalAInsertar.getPassword());
        return comunalDepositRepository.save(depositoComunal);
    }


}
