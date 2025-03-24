package com.sofia.security.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


/*
 * JWT composed of 
 * 1. The HEADER
 * 2. The PAYLOAD
 * 3. The Signature 
 */
@Service
public class JwtService {

    private static final String SECRET_KEY = "8CFEC8D41E71ED413075F127EAE12BDB4325ACDF7033357994D769A931E897E4"; 
    // return string user name
    // takes string jwt token 
    public String extractUsername(String token) {
       return extractClaim(token, Claims::getSubject);
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){ 
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){ 
        return generateToken(new HashMap<>(), userDetails); 
    }
    // function to generate token 
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails){
        return Jwts
        .builder()
        .setClaims(extractClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact(); 
    }

    // validate if the token belongs to correct user 
    public boolean isTokenValid(String token, UserDetails userDetails){ 
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token); 
    }

    // check if the token is expired 
    private boolean isTokenExpired(String token){ 
        return extractExpiration(token).before(new Date()); // todays date
    }

    // check to see if data is valid or expired 
    private Date extractExpiration(String token){ 
        return extractClaim(token, Claims:: getExpiration); 
    }

    private Claims extractAllClaims(String token){ 
        return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody(); 
    }

    private Key getSignInKey(){ 
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY); 
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
