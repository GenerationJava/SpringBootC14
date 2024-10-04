package generation.springhospital.services;

import generation.springhospital.models.Usuario;
import generation.springhospital.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).get();
    }

    public Usuario saveUsuario(Usuario usuarioNuevo) {
        return usuarioRepository.save(usuarioNuevo);
    }

}
