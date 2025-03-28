
package com.sofia.security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * Authenticate an existing user with two endpoints
 * 1. register
 * 2. authenticator
 * 
 * @author sofiaschnurrenberger
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service; 

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register( 
        @RequestBody RegisterRequest request
    ) { 
        return ResponseEntity.ok(service.register(request)); 
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register( 
        @RequestBody AuthenticationRequest request
    ) { 
        return ResponseEntity.ok(service.authenticate(request)); 
    }
}
