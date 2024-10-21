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

import com.example.demo.repository.PrincipalDepositRepository;

@Configuration
public class ApplicationConfigurationPrincipalDeposit {
    private final PrincipalDepositRepository principalDepositRepository;

    public ApplicationConfigurationPrincipalDeposit(PrincipalDepositRepository principalDepositRepository) {
        this.principalDepositRepository = principalDepositRepository;
    }

    @Bean
    public UserDetailsService userDetailsServicePrincipal() {
        return username -> principalDepositRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public BCryptPasswordEncoder principalPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider principalAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServicePrincipal());
        authProvider.setPasswordEncoder(principalPasswordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager principalAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(principalAuthenticationProvider());
    }
}