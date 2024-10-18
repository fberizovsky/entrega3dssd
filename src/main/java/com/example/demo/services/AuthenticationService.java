package com.example.demo.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.PrincipalDeposit;
import com.example.demo.models.dtos.CrearDepositoPrincipalDTO;
import com.example.demo.models.dtos.DevolverDepositoPrincipalDTO;
import com.example.demo.repository.PrincipalDepositRepository;

@Service
public class AuthenticationService {
    private final PrincipalDepositRepository principalDepositRepositoryRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        PrincipalDepositRepository principalDepositRepositoryRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.principalDepositRepositoryRepository = principalDepositRepositoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PrincipalDeposit signup(CrearDepositoPrincipalDTO input) {
        PrincipalDeposit user = new PrincipalDeposit(input.getName(), passwordEncoder.encode(input.getPassword()));
        return principalDepositRepositoryRepository.save(user);
    }

    public PrincipalDeposit authenticate(DevolverDepositoPrincipalDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getName(),
                        input.getPassword()
                )
        );

        return PrincipalDepositRepository.findByEmail(input.getName())
                .orElseThrow();
    }
}