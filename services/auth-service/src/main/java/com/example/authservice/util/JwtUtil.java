package com.example.authservice.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key secretKey;

    public JwtUtil(
            @Value("${spring.security.jwt.secret}") String secret) {
        byte [] keyBytes = Base64.getDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 3600 * 10))
                .signWith(secretKey)
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey)secretKey)
                    .build()
                    .parseSignedClaims(token);
        }catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature");
        }catch (JwtException e){
            throw new JwtException("Invalid JWT");

        }

    }

    public String getEmailFromToken(String token) {
        SecretKey key = (SecretKey) secretKey; // lub stwórz SecretKey z bytesów, jeśli potrzebujesz

        String email = Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();

        System.out.println("Email z tokena: " + email);

        return email;
    }
}
