package com.sofia.security.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author sofiaschnurrenberger
 */


@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter{
    private final JwtService jwtService; 
    private final UserDetailsService userDetailsService; 
    
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt; // token, should start with "Bearer"
        final String userEmail; 

        // (init check) checking the jwt token ...
        if(authHeader == null || !authHeader.startsWith("Bearer ")){ 
            filterChain.doFilter(request, response); // pass the request and response to next filter
            return; // dont want to continue with rest of execution 
        }

        // extract the token from the auth header ... 
        jwt = authHeader.substring(7); 

        // extract the user email ..
        userEmail = jwtService.extractUsername(jwt); // todo extract the userEmail from jwt token 

        // check if already authenticated so dont have to repeat process
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // need to get user from database 
            UserDetails  userDetails = this.userDetailsService.loadUserByUsername(userEmail); 
            // check if token is still valid 
            if(jwtService.isTokenValid(jwt, userDetails)){
                // update the security contect and send request to dispatch server 
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // enforce token with details of request
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // pass the next filter to be executed 
        filterChain.doFilter(request, response);
    }

}
