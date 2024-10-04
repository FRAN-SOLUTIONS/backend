package com.fran.FRAN.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

public class JWTCreator {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLES_AUTHORITIES = "authorities";

    // Método para criar um JWT
    public static String create(String prefix, String key, JWTObject jwtObject) {
        // Decodifica a chave base64
        byte[] keyBytes = Decoders.BASE64.decode(key);
        Key secretKey = Keys.hmacShaKeyFor(keyBytes); // Cria a chave secreta

        // Cria o token JWT
        String token = Jwts.builder()
                .setSubject(jwtObject.getSubject())
                .setIssuedAt(jwtObject.getIssuedAt())
                .setExpiration(jwtObject.getExpiration())  // Usando o novo método expiration()
                .claim(ROLES_AUTHORITIES, checkRoles(jwtObject.getRoles()))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        return prefix + " " + token;
    }

    // Método para decodificar um JWT
    public static JWTObject create(String token, String prefix, String key)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {
        JWTObject object = new JWTObject();
        token = token.replace(prefix, "");

        // Decodifica a chave base64
        byte[] keyBytes = Decoders.BASE64.decode(key);
        Key secretKey = Keys.hmacShaKeyFor(keyBytes); // Cria a chave secreta

        // Analisa o token JWT
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        object.setSubject(claims.getSubject());
        object.setExpiration(claims.getExpiration());
        object.setIssuedAt(claims.getIssuedAt());
        object.setRoles((List<String>) claims.get(ROLES_AUTHORITIES));
        return object;
    }

    private static List<String> checkRoles(List<String> roles) {
        return roles.stream()
                .map(s -> "ROLE_".concat(s.replaceAll("ROLE_", "")))
                .collect(Collectors.toList());
    }
}
