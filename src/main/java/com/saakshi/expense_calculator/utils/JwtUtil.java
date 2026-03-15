package com.saakshi.expense_calculator.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private  final SecretKey key = Jwts.SIG.HS256.key().build();
    private final long EXPIRATION_TIME = 86400000;

    //Generate token
    public String generateToken(String username)
    {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    //Extract username from token
    public String extractUsername(String token)
    {
        return getClaims(token).getSubject();
    }

    //Validate Token
    public boolean validateToken(String token, String username)
    {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token)
    {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
