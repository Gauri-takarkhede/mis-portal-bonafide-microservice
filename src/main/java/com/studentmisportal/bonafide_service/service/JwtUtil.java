package com.studentmisportal.bonafide_service.service;

//import com.studentmisportal.backend.entity.User;
import com.studentmisportal.bonafide_service.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(UserDto user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("username", user.getUsername());
        claims.put("mis", user.getMis());

        return Jwts.builder()
                .addClaims(claims)
                .setSubject(user.getMis())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDto user) {

        return Jwts.builder()
                .setSubject(user.getMis())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String extractMis(String token) {
        return extractAllClaims(token).get("mis", String.class);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }


    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, String mis) {
        try {
            final String userMis = extractSubject(token);
            return (userMis.equals(mis) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}