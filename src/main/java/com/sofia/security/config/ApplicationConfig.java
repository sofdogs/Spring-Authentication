
package com.sofia.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sofia.security.user.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Holds all application configurations 
 * create bean of user deatils service 
 * 
 * @author sofiaschnurrenberger
 * 
 */
 @Configuration
 @RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository; 

    @Bean
    public UserDetailsService userDetailsService(){ 
        return username -> repository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    // data access obj to fetch user details and encode password
    @Bean
    public AuthenticationProvider authenticationProvider(){ 
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); 
        // specify properties 
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider; 
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{ 
        return config.getAuthenticationManager(); 
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
}
