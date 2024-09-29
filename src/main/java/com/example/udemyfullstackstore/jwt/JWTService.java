package com.example.udemyfullstackstore.jwt;

import com.example.udemyfullstackstore.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;

@Service
@RequiredArgsConstructor
public class JWTService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long expiration;

    public String buildToken(
            String email,
            String username,
            String password,
            UserRole role
    ) {
        return Jwts.builder()
                .claim("email", email)
                .claim("username", username)
                .claim("password", password)
                .claim("role", role)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSingInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmail(String token) {
        Claims claims = getAllClaims(token);
        return (String) claims.get("email");
    }

    public String getUsername(String token) {
        Claims claims = getAllClaims(token);
        return (String) claims.get("username");
    }

    public String getPassword(String token) {
        Claims claims = getAllClaims(token);
        return (String) claims.get("password");
    }

    public UserRole getRole(String token) {
        Claims claims = getAllClaims(token);
        return UserRole.valueOf((String) claims.get("role"));
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSingInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
