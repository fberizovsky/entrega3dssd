package com.example.demo.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
import com.example.demo.models.Item;
import com.example.demo.models.Orden;
import com.example.demo.models.PrincipalDeposit;
import com.example.demo.models.dtos.CrearOrdenDTO;
import com.example.demo.models.enums.Estado;
import com.example.demo.repository.OrdenRepository;
import com.example.demo.repository.PrincipalDepositRepository;



@RestController
@RequestMapping("/api/orden")
public class OrdenController {
    
    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired PrincipalDepositRepository principalDepositRepository;


    /* Se le puede mandar esto
    {
        "estado": "PENDIENTE",
        "principalDepositId": 1,
        "items": [
            {
            "nombre": "Madera",
            "cantidad": 5
            },
            {
            "nombre": "Clavos",
            "cantidad": 10
            }
        ]
    }

     */
    @PostMapping
    public ResponseEntity<?> crearOrden(@RequestBody CrearOrdenDTO crearOrdenDTO) throws UnsupportedEncodingException {
        Orden orden = new Orden();
        for (Item item : crearOrdenDTO.getItems()) {
            orden.addItem(item);
        }
        PrincipalDeposit depositoPrincipal = principalDepositRepository.findById(crearOrdenDTO.getPrincipalDepositId()).get();
        orden.setPrincipalDeposit(depositoPrincipal);
        depositoPrincipal.addOrder(orden);
        orden.setEstado(crearOrdenDTO.getEstado());
        Orden ordenAGuardar = ordenRepository.save(orden);
        return ResponseEntity.ok(ordenAGuardar);
    }

    @GetMapping
    public List<Orden> obtenerOrdenes() {
        List<Orden> ordenes = ordenRepository.findAll();
        return ordenes;
    }

    @PutMapping("/reservar/{orderId}")
    public ResponseEntity<?> reservarOrden(@PathVariable Long orderId, @RequestBody ComunalDeposit comunalDeposit) {
        Orden orden = ordenRepository.findById(orderId).get();
        orden.setComunalDeposit(comunalDeposit);
        orden.setEstado(Estado.RESERVADO);
        ordenRepository.save(orden);
        return ResponseEntity.ok(orden);
    }

    @PutMapping("/entregar/{orderId}")
    public ResponseEntity<?> entregarOrden(@PathVariable Long orderId) {
        Orden orden = ordenRepository.findById(orderId).get();
        if (orden.getEstado() != Estado.RESERVADO) {
            return ResponseEntity.badRequest().body("La orden no está reservada");
        }
        orden.setEstado(Estado.ENTREGADO);
        ordenRepository.save(orden);
        return ResponseEntity.ok(orden);
    }

}