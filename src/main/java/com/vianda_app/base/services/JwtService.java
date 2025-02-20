package com.vianda_app.base.services;

import com.vianda_app.base.entities.Administrador;
import com.vianda_app.base.entities.Cliente;
import com.vianda_app.base.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String generateToken(final Usuario usuario) {
        return buildToken(usuario, jwtExpiration);
    }

    public String generateRefreshToken(final Usuario usuario) {
        return buildToken(usuario, refreshExpiration);
    }

//    public String generateAdminToken(final Administrador usuario) {
//        return buildAdminToken(usuario, jwtExpiration);
//    }
//
//    public String generateAdminRefreshToken(final Administrador usuario) {
//        return buildAdminToken(usuario, refreshExpiration);
//    }

//    public String buildClienteToken(final Cliente usuario, final long expiration) {
//        return Jwts.builder()
//                .id(usuario.getId().toString())
//                .claims(Map.of(
//                        "name", usuario.getNombre(),
//                        "userId", usuario.getId(),
//                        "area", usuario.getArea().getNombre()
//                        ))
//                .subject(usuario.getNombre())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(getSignInKey())
//                .compact();
//    }

    public String buildToken(final Usuario usuario, final long expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", usuario.getNombre());
        claims.put("userId", usuario.getId());

        if (usuario instanceof Cliente cliente) {
            claims.put("area", cliente.getArea().getNombre());
        } else if (usuario instanceof  Administrador administrador) {
            claims.put("distribuidora", administrador.getDistribuidora().getNombre());
        }

        return Jwts.builder()
                .id(usuario.getId().toString())
                .claims(claims)
                .subject(usuario.getNombre())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(final String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    public String extractRol(final String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.get("role", String.class);
    }

    public boolean isTokenValid(final String token, final Usuario usuario) {
        final String userNombre = extractUsername(token);
        return (userNombre.equals(usuario.getNombre())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(final String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }
}
