package generation.springhospital.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Parsear el token JWT de la solicitud
            String jwt = parseJwt(request);
            // Verificar si el token es válido
            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
                // Obtener el nombre de usuario del token JWT
                String email = jwtUtil.getUsernameFromJwtToken(jwt);
                // Cargar los detalles del usuario desde la base de datos
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // Crear una autenticación de usuario basada en los detalles del usuario
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // Establecer los detalles de la autenticación basados en la solicitud HTTP
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer la autenticación del usuario en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Manejar cualquier error que ocurra durante el proceso de autenticación
            logger.error("Cannot set user authentication: {}", e);
        }
        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Método para parsear el token JWT de la solicitud HTTP
    private String parseJwt(HttpServletRequest request) {
        // Obtener el token del encabezado de autorización
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            // Eliminar el prefijo "Bearer " del token JWT y devolverlo
            return headerAuth.substring(7);
        }
        // Devolver null si no se encuentra el token en el encabezado de autorización
        return null;
    }
}
