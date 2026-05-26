package com.davidrt301.medicare.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.davidrt301.medicare.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expirationMs}")
    private Long expirationTime;

    /**
     * Obtiene la clave firmada convertida desde la clave secreta
     * @return Key para firmar y validar tokens JWT
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genera un token JWT con el rol del usuario
     * 
     * @param rol Rol del usuario para incluir en el token
     * @return Token JWT generado
     */
    public String generateToken(String nombre, Role role) {
        return Jwts.builder()
                .subject(nombre)
                .claim("role", role.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey()) // Firma del Token
                .compact();// convierrte a String
    }

    /**
     * Obtiene el nombre de usuario del token JWT
     * @param token Token JWT
     * @return Nombre de usuario extraído del token
     */
    public String getUsuarioFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    /*
     * Valida si un token JWT es válido
     * @param token Token JWT a validar
     * @return true si el token es válido, false en caso contrario
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
