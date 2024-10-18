package generation.springhospital.api;

import generation.springhospital.DTO.LoginDTO;
import generation.springhospital.DTO.RegistroDTO;
import generation.springhospital.models.Doctor;
import generation.springhospital.models.Paciente;
import generation.springhospital.models.TipoUsuario;
import generation.springhospital.models.Usuario;
import generation.springhospital.security.JwtUtil;
import generation.springhospital.security.UserDetailsImpl;
import generation.springhospital.services.PacienteServiceImpl;
import generation.springhospital.services.UsuarioServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthRestController {

    private final AuthenticationManager authenticationManager;

    private final UsuarioServiceImpl usuarioService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser( @RequestBody LoginDTO loginRequest) {

        // Autentica al usuario utilizando el gestor de autenticación de Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        // Establece la autenticación en el contexto de seguridad de Spring Security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Obtiene los detalles del usuario autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Genera un token JWT para el usuario autenticado
        String jwtToken = jwtUtils.generateJwtToken(userDetails);

        // Retorna una respuesta exitosa con el token JWT y detalles del usuario
        return new ResponseEntity<>(new JwtResponse(jwtToken, userDetails.getEmail()), HttpStatus.OK);
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registerUser( @RequestBody RegistroDTO solicitudRegistro) {

        /*
        // Verifica si el nombre de usuario ya está en uso
        if (usuarioService.existeUsuarioPorUsername(solicitudRegistro.getUsername())) {
            return ResponseEntity.badRequest().body(new String("Error: El username ya está en uso!"));
        }

        // Verifica si el correo electrónico ya está en uso
        if (usuarioService.existeUsuarioPorEmail(solicitudRegistro.getEmail())) {
            return ResponseEntity.badRequest().body(new String("Error: El email ya está en uso"));
        }*/

        // 1. Crea una nueva cuenta de usuario tipo paciente
        // Crear un nuevo objeto `Paciente` y asignar los atributos del `Usuario`
        // Asignamos el tipo de usuario como PACIENTE
        // Asigna otros atributos específicos del paciente
        Paciente nuevoPaciente = new Paciente();
        nuevoPaciente.setNombre(solicitudRegistro.getNombre());
        nuevoPaciente.setEmail(solicitudRegistro.getEmail());
        nuevoPaciente.setPassword(passwordEncoder.encode(solicitudRegistro.getPassword()));
        nuevoPaciente.setTipo(TipoUsuario.PACIENTE);



        // 2. Guardar la instancia del Doctor usando el servicio `usuarioService`
        Usuario usuarioGuardado = usuarioService.saveUsuario(nuevoPaciente);

        // 3. Retornar la respuesta indicando que el doctor fue creado correctamente
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);

    }

    @PostMapping("/registro/doctor")
    public ResponseEntity<Usuario> registerDoctor(@RequestBody RegistroDTO solicitudRegistro) {

        //1. Crea una nueva cuenta de usuario tipo paciente
        //Crear un nuevo objeto `DOCTOR` y asignar los atributos del `Usuario`
        //Asignamos el tipo de usuario como DOCTOR
        //Asigna otros atributos específicos del DOCTOR

        Doctor nuevoDoctor = new Doctor();
        nuevoDoctor.setNombre(solicitudRegistro.getNombre());
        nuevoDoctor.setEmail(solicitudRegistro.getEmail());
        nuevoDoctor.setPassword(passwordEncoder.encode(solicitudRegistro.getPassword()));
        nuevoDoctor.setTipo(TipoUsuario.DOCTOR);


        // 2. Guardar la instancia del Doctor usando el servicio `usuarioService`
        Usuario usuarioGuardado = usuarioService.saveUsuario(nuevoDoctor);

        // 3. Retornar la respuesta indicando que el doctor fue creado correctamente
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
    }

    // Clase interna que representa la respuesta del token JWT
    @AllArgsConstructor
    @Getter
    @Setter
    public class JwtResponse {
        private String token;
        private String email;
    }
}

