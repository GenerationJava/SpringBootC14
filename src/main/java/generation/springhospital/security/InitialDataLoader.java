package generation.springhospital.security;

import generation.springhospital.models.TipoUsuario;
import generation.springhospital.models.Usuario;
import generation.springhospital.services.UsuarioService;
import generation.springhospital.services.UsuarioServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitialDataLoader {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Inicialización de datos con usuario admin
    @PostConstruct
    public void init() {
        if (usuarioService.existsUsuarioByEmail("admin@hospital.com")) {
            System.out.println("Ya existe un usuario ADMIN");
        } else {
            Usuario admin = Usuario.builder()
                    .nombre("Administrador")
                    .email("admin@hospital.com")
                    .password(passwordEncoder.encode("admin123")) // Codifica la contraseña
                    .tipo(TipoUsuario.ADMIN) // Asigna el tipo de usuario ADMIN
                    .build();
            usuarioService.saveUsuario(admin);
        }
    }
}
