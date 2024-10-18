package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ComunalDeposit;
import com.example.demo.models.MaterialComunalDeposit;
import com.example.demo.models.PrincipalDeposit;
import com.example.demo.models.dtos.CrearDepositoPrincipalDTO;
import com.example.demo.models.dtos.CrearMaterialComunalDepositDTO;
import com.example.demo.models.dtos.DevolverDepositoComunalDTO;
import com.example.demo.models.dtos.DevolverDepositoPrincipalDTO;
import com.example.demo.models.dtos.DevolverOrdenDTO;
import com.example.demo.models.enums.Material;
import com.example.demo.repository.ComunalDepositRepository;
import com.example.demo.repository.MaterialComunalDepositRepository;
import com.example.demo.repository.PrincipalDepositRepository;
import com.example.demo.validators.MaterialValidator;

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
                        depositoPrincipal.getName(), 
                        depositoPrincipal.getOrders().stream().map(order -> new DevolverOrdenDTO(order.getId(), order.getPrincipalDeposit().getName(), order.getEstado(), order.getItems())).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    /**
     * Crea un nuevo depósito principal.
     *
     * @param depositoPrincipal El objeto PrincipalDeposit que contiene la información del depósito a crear.
     * @return El objeto PrincipalDeposit guardado en el repositorio.
     */
    @PostMapping
    public ResponseEntity<DevolverDepositoPrincipalDTO> crearDepositoPrincipal(@RequestBody CrearDepositoPrincipalDTO depositoPrincipalAInsertar) {
        PrincipalDeposit depositoPrincipal = new PrincipalDeposit(depositoPrincipalAInsertar.getName(), depositoPrincipalAInsertar.getPassword());
        depositoPrincipal = principalDepositRepository.save(depositoPrincipal);
        DevolverDepositoPrincipalDTO responseDTO = new DevolverDepositoPrincipalDTO(depositoPrincipal.getId(), depositoPrincipal.getName());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/asignarMaterial")
    public ResponseEntity<?> asignarMaterial(@RequestBody CrearMaterialComunalDepositDTO request) {
        
        // Cuando haya auth, se debe obtener el deposito principal del usuario autenticado. La linea siguiente es solo un ejemplo.
        PrincipalDeposit depositoPrincipal = principalDepositRepository.findById(1L).get();

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

    @GetMapping("/materiales")
    public ResponseEntity<?> obtenerDepositosComunalesPorMaterial(@RequestParam Material material) {
        List<MaterialComunalDeposit> depositosPrincipales = materialComunalDepositRepository.findByMaterial(material);
        // List<ComunalDeposit> depositosComunales = depositosPrincipales.stream()
        //         .map(MaterialComunalDeposit::getComunalDeposit)
        //         .collect(Collectors.toList());
        List<DevolverDepositoComunalDTO> responseDTOs = depositosPrincipales.stream()
                .map(materialComunalDeposit -> new DevolverDepositoComunalDTO(
                        materialComunalDeposit.getComunalDeposit().getId(), 
                        materialComunalDeposit.getComunalDeposit().getUsername()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }
}
