package com.example.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] SWAGGER_WHITELIST = {
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/swagger-resources"
    };

    public SecurityConfiguration(
        JwtAuthenticationFilter jwtAuthenticationFilter,
        AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilita CORS
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(SWAGGER_WHITELIST).permitAll() // Permite acceso a Swagger sin autenticación
                .requestMatchers("/users/**").authenticated() // Protege los endpoints /users/**
                .requestMatchers("/auth/**").permitAll() // Permite acceso sin autenticación a /auth/**
                .requestMatchers("/api/colecta").permitAll()
                .anyRequest().permitAll() // Permite acceso al resto de las rutas
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No mantiene sesiones
            )
            .authenticationProvider(authenticationProvider) // Configura el proveedor de autenticación
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Añade el filtro JWT

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Permitir el origen del frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Encabezados permitidos
        configuration.addExposedHeader("Authorization"); // Exponer encabezado de autorización
        configuration.setAllowCredentials(true); // Permitir envío de credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica configuración a todas las rutas
        return source;
    }
}
