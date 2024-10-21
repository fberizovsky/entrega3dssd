package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.repository.ComunalDepositRepository;

@Configuration
public class ApplicationConfigurationComunalDeposit {
    private final ComunalDepositRepository comunalDepositRepository;

    public ApplicationConfigurationComunalDeposit(ComunalDepositRepository comunalDepositRepository) {
        this.comunalDepositRepository = comunalDepositRepository;
    }

    @Bean
    public UserDetailsService userDetailsServiceComunal() {
        return username -> comunalDepositRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public BCryptPasswordEncoder comunalPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider comunalAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceComunal());
        authProvider.setPasswordEncoder(comunalPasswordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager comunalAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(comunalAuthenticationProvider());
    }
}