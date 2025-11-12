package com.springApp.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    /*secreteKey -> nos ayudara a firmar los metodos para que se tenga la autorizacion legitima
     * la cual vamos a generar el token*/
    @Value("${jwt.secret.key}")
    private String secretKey;

    //tiempo de validez en milisegundos configurado para 1 dia
    @Value("${jwt.time.expiration}")
    private Long timeExpiration;

    //generar token de acceso
    public String generateAccessToken(String username){
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(timeExpiration);

        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(getSigningKey(), Jwts.SIG.HS256) // ✅ Nueva forma
                .compact();
    }

    //Obtener firma del token
    public javax.crypto.SecretKey  getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Valida el token y retorna true si es válido.
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    /*Indica con qué clave secreta se debe verificar la firma del token (reemplazo de setSigningKey()).*/
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token); //Parsea el token firmado (JWS) y valida la firma automáticamente.
            return true;
        } catch (Exception e) {
            log.error("Token invalido, error: ".concat(e.getMessage()));
            return false;
        }
    }

    /**
     * Obtiene el nombre de usuario desde el token.
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                /*Indica con qué clave secreta se debe verificar la firma del token (reemplazo de setSigningKey()).*/
                .verifyWith(getSigningKey())
                .build() //Construye el parser listo para usarse.
                .parseSignedClaims(token)//Parsea el token firmado (JWS) y valida la firma automáticamente.
                .getPayload()//Obtiene el contenido (claims) del token.
                .getSubject(); //Devuelve el campo sub (subject), que en este caso es el username.
    }

}
