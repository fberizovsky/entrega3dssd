package com.example.demo.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.PrincipalDeposit;
import com.example.demo.models.dtos.CrearDepositoPrincipalDTO;
import com.example.demo.models.dtos.IniciarDepositoPrincipalDTO;
import com.example.demo.repository.PrincipalDepositRepository;

@Service
public class AuthenticationServicePrincipal {
    private final PrincipalDepositRepository principalDepositRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationServicePrincipal(
        PrincipalDepositRepository principalDepositRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.principalDepositRepository = principalDepositRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PrincipalDeposit signup(CrearDepositoPrincipalDTO input) {
        PrincipalDeposit user = new PrincipalDeposit(input.getName(), passwordEncoder.encode(input.getPassword()));
        return principalDepositRepository.save(user);
    }

    public PrincipalDeposit authenticate(IniciarDepositoPrincipalDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getName(),
                        input.getPassword()
                )
        );

        return principalDepositRepository.findByName(input.getName())
                .orElseThrow();
    }
}