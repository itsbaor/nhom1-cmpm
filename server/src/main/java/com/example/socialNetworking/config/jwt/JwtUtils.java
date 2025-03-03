package com.example.socialNetworking.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Tạo Access Token (15 phút)
    public String generateAccessToken(Authentication auth){
        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 3 * 60 * 1000))
                .claim("email", auth.getName())
                .claim("authorities", auth.getAuthorities())
                .signWith(key)
                .compact();
        return jwt;
    }

    public String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(jwt).getBody();

        String email = String.valueOf(claims.get("email"));
        return email;
    }

    public String getAuthoritiesFromToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(jwt).getBody();

        String authorities = String.valueOf(claims.get("authorities"));

        return authorities;
    }

    // Kiểm tra token hết hạn
    public boolean isTokenExpired(String jwt) {
        jwt = jwt.substring(7); // Bỏ phần "Bearer "
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(jwt).getBody();
        return claims.getExpiration().before(new Date());

    }
}
