package com.example.study_platform.auth.security;

import com.example.study_platform.auth.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("user_id", Long.class));
    }



    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken (UserDetails userDetails, Long userId) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userId);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        System.out.printf("roles: %s\n", roles);
        claims.put("roles", roles);
        System.out.println("Roles in token: " + userDetails.getAuthorities());
        return createToken(claims, ((CustomUserDetails) userDetails).getEmail());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String emailFromToken = extractUsername(token); // subject теперь email
        final String email = ((CustomUserDetails) userDetails).getEmail();
        return (emailFromToken.equals(email) && !isTokenExpired(token));
    }

    Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();

    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseClaimsJws(token)
                .getPayload();


    }
    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }
    public Long getUserIdFromToken(String token) {

        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("user_id", Long.class); // Извлекаем userId
    }



}
