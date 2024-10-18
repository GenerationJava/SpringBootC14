package generation.springhospital.services;

import generation.springhospital.models.Usuario;

public interface UsuarioService {

    Usuario findById(Long id);

    Usuario saveUsuario(Usuario usuarioNuevo);

    Boolean existsUsuarioByEmail(String email);

}
