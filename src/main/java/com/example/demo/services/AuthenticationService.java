package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.ComunalDeposit;
import com.example.demo.models.PrincipalDeposit;
import com.example.demo.models.User;
import com.example.demo.models.dtos.LoginUserDto;
import com.example.demo.models.dtos.RegisterUserDto;
import com.example.demo.models.enums.TipoUsuario;
import com.example.demo.repository.ComunalDepositRepository;
import com.example.demo.repository.PrincipalDepositRepository;
import com.example.demo.repository.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    @Autowired
    private PrincipalDepositRepository principalDepositRepository;

    @Autowired
    private ComunalDepositRepository comunalDepositRepository;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(RegisterUserDto input) {
        
        if (input.getTipoUsuario().equals(TipoUsuario.DEPOSITO_PRINCIPAL)) {
            PrincipalDeposit user = new PrincipalDeposit(input.getFullName(), input.getEmail(), passwordEncoder.encode(input.getPassword()), input.getTipoUsuario());
            user = principalDepositRepository.save(user);
        }
        else {
            ComunalDeposit user = new ComunalDeposit(input.getFullName(), input.getEmail(), passwordEncoder.encode(input.getPassword()), input.getTipoUsuario());
            user = comunalDepositRepository.save(user);
        }
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

    public Object getSessionUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (currentUser.getTipoUsuario().equals(TipoUsuario.DEPOSITO_PRINCIPAL)) {
            Optional<PrincipalDeposit> principalDeposit = principalDepositRepository.findById(currentUser.getId());
            return principalDeposit.orElse(null);
        }
        if (currentUser.getTipoUsuario().equals(TipoUsuario.DEPOSITO_COMUNAL)) {
            Optional<ComunalDeposit> comunalDeposit = comunalDepositRepository.findById(currentUser.getId());
            return comunalDeposit.orElse(null);
        }
        return null;
    }

}