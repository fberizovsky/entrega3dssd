package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.UserService;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

   
    /**
     * Requiere autenticación. Metodo que devueve los datos del usuario logueado.
     *
     * @return ResponseEntity con los datos del usuario logueado.
     */
    @GetMapping("/me")
    public ResponseEntity<?> authenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + auth.getAuthorities());
        return ResponseEntity.ok(authenticationService.getSessionUser());
    }
    /**
     * Requiere autenticación. Metodo que devueve todos los usuarios.
     *
     * @return ResponseEntity con todos los usuarios existentes en la base de datos.
     */
    @GetMapping("/") 
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
}
