package com.sofia.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sofia.security.config.JwtService;
import com.sofia.security.user.Role;
import com.sofia.security.user.User;
import com.sofia.security.user.UserRepository;
/* 
import com.sofia.security.token.Token;
import com.sofia.security.token.TokenRepository;
import com.sofia.security.token.TokenType;
*/
import lombok.RequiredArgsConstructor;

/**
 *
 * @author sofiaschnurrenberger
 */

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository; 
    private final JwtService jwtService; 
    private final PasswordEncoder  passwordEncoder; 
    private final AuthenticationManager authManager;
    
    // allow use to create a user, save to database, and generate a token 
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
        repository.save(user); // save user we just created
        var jwtToken = jwtService.generateToken(user); 
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build(); 
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // check if username & password are correct ...
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword())
        );

        // generate token and send back ...
        var user = repository.findByEmail(request.getEmail()).orElseThrow(); 
        var jwtToken = jwtService.generateToken(user); 

        // return auth response
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build(); 
    }

}
