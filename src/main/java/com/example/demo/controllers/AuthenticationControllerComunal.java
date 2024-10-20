package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ComunalDeposit;
import com.example.demo.models.LoginResponse;
import com.example.demo.models.dtos.CrearDepositoComunalDTO;
import com.example.demo.models.dtos.IniciarDepositoComunalDTO;
import com.example.demo.services.AuthenticationServiceComunal;
import com.example.demo.services.JwtService;

@RequestMapping("/authComunal")
@RestController
public class AuthenticationControllerComunal {
    private final JwtService jwtService;
    
    private final AuthenticationServiceComunal authenticationServiceComunal;

    public AuthenticationControllerComunal(JwtService jwtService, AuthenticationServiceComunal authenticationServiceComunal) {
        this.jwtService = jwtService;
        this.authenticationServiceComunal = authenticationServiceComunal;
    }

    @PostMapping("/signup")
    public ResponseEntity<ComunalDeposit> register(@RequestBody CrearDepositoComunalDTO crearDepositoComunalDTO) {
        ComunalDeposit registeredUser = authenticationServiceComunal.signup(crearDepositoComunalDTO);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody IniciarDepositoComunalDTO iniciarDepositoComunalDTO) {
        ComunalDeposit authenticatedUser = authenticationServiceComunal.authenticate(iniciarDepositoComunalDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken,jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
