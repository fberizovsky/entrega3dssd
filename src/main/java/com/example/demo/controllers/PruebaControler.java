package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PruebaControler {

    /**
     * Endpoint de prueba para verificar que el controlador funciona.
     *
     * @return Respuesta simple con un mensaje de éxito.
     */
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("¡El endpoint está funcionando!");
    }
}
