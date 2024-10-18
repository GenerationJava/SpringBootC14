package generation.springhospital.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    // Logger para registrar mensajes de información, advertencia y error
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // Clave secreta utilizada para firmar y verificar el token JWT
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Tiempo de expiración del token JWT en milisegundos
    @Value("${jwt.expiration.ms}")
    private int jwtExpirationMs;

    // Método para generar un token JWT a partir de los datos del usuario autenticado
    public String generateJwtToken(UserDetailsImpl userPrincipal) {
        return generateTokenFromUsername(userPrincipal.getEmail());
    }

    // Método para obtener el nombre de usuario (subject) desde un token JWT
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // Método para validar un token JWT
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret.getBytes()).build().parseClaimsJws(authToken);
            return true; // El token es válido
        } catch (ExpiredJwtException e) {
            logger.error("El JWT Token ya expiró: {}", e.getMessage()); // El token ha expirado
        } catch (MalformedJwtException e) {
            logger.error("JWT Token inválido: {}", e.getMessage()); // El token es inválido
        } catch (Exception e) {
            logger.error("Error al validar el JWT Token: {}", e.getMessage()); // Otro error durante la validación del token
        }
        return false; // El token no es válido
    }

    // Método para obtener la clave utilizada para firmar y verificar el token
    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Método para generar un token JWT a partir del nombre de usuario (subject)
    private String generateTokenFromUsername(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }


}
