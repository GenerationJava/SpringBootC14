package generation.springhospital.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    // Método que se invoca cuando se produce una excepción de autenticación
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Registrar un mensaje de error de autenticación no autorizada
        logger.error("Unauthorized error: {}", authException.getMessage());

        // Establecer el tipo de contenido de la respuesta como JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Establecer el estado de la respuesta como no autorizado (401)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Crear un mapa para almacenar los detalles del error de autenticación
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED); // Estado de la respuesta
        body.put("error", "Unauthorized"); // Tipo de error
        body.put("message", authException.getMessage()); // Mensaje de error
        body.put("path", request.getServletPath()); // Ruta del servlet que causó el error

        // Convertir el mapa a JSON y escribirlo en el cuerpo de la respuesta
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}