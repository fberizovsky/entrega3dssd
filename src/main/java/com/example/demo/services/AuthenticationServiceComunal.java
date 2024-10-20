package com.example.demo.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.ComunalDeposit;
import com.example.demo.models.dtos.CrearDepositoComunalDTO;
import com.example.demo.models.dtos.IniciarDepositoComunalDTO;
import com.example.demo.repository.ComunalDepositRepository;

@Service
public class AuthenticationServiceComunal {
    private final ComunalDepositRepository comunalDepositRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceComunal(
        ComunalDepositRepository comunalDepositRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.comunalDepositRepository = comunalDepositRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ComunalDeposit signup(CrearDepositoComunalDTO input) {
        ComunalDeposit user = new ComunalDeposit(input.getName(), passwordEncoder.encode(input.getPassword()));
        return comunalDepositRepository.save(user);
    }

    public ComunalDeposit authenticate(IniciarDepositoComunalDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getName(),
                        input.getPassword()
                )
        );

        return comunalDepositRepository.findByName(input.getName())
                .orElseThrow();
    }
}