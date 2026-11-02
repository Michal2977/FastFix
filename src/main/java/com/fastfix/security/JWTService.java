package com.fastfix.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTService {


    private final String SECRET;

    public JWTService( @Value("${jwt.secret}") String secret) {
        SECRET = secret;
    }
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String email){
        return Jwts.builder().subject(email).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()
        + 86400000)).signWith(secretKey()).compact();
    }

    public String extractEmail(String token){
        Claims claims = Jwts.parser().verifyWith(secretKey()).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    public boolean isValid(String token){
        try {
            Jwts.parser().verifyWith(secretKey()).build().parseSignedClaims(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}


