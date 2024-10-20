package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.LoginResponse;
import com.example.demo.models.PrincipalDeposit;
import com.example.demo.models.dtos.CrearDepositoPrincipalDTO;
import com.example.demo.models.dtos.IniciarDepositoPrincipalDTO;
import com.example.demo.services.AuthenticationServicePrincipal;
import com.example.demo.services.JwtService;

@RequestMapping("/authPrincipal")
@RestController
public class AuthenticationControllerPrincipal {
    private final JwtService jwtService;
    
    private final AuthenticationServicePrincipal authenticationServicePrincipal;

    public AuthenticationControllerPrincipal(JwtService jwtService, AuthenticationServicePrincipal authenticationServicePrincipal) {
        this.jwtService = jwtService;
        this.authenticationServicePrincipal = authenticationServicePrincipal;
    }

    @PostMapping("/signup")
    public ResponseEntity<PrincipalDeposit> register(@RequestBody CrearDepositoPrincipalDTO crearDepositoPrincipalDTO) {
        PrincipalDeposit registeredUser = authenticationServicePrincipal.signup(crearDepositoPrincipalDTO);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody IniciarDepositoPrincipalDTO iniciarDepositoPrincipalDTO) {
        PrincipalDeposit authenticatedUser = authenticationServicePrincipal.authenticate(iniciarDepositoPrincipalDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken,jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
