package generation.springhospital.services;

import generation.springhospital.email.EmailService;
import generation.springhospital.models.Notificacion;
import generation.springhospital.models.Usuario;
import generation.springhospital.repositories.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//Anotaciones
@Service
public class NotificacionServiceImpl implements NotificacionService {

    //Inyección de dependencias
    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private EmailService emailService;


    //Método para crear notificación
    public Notificacion crearNotificacion(Usuario usuario, String mensaje) {
        /***NOTIFICACIÓN DENTRO DEL SISTEMA***/
        //Creamos nuestra nueva instancia de la notificación con patrón Builder()
        Notificacion notificacion = Notificacion.builder()
                .usuario(usuario)
                .mensaje(mensaje)
                //Seteamos la fecha y hora de creación a través del método estático now() de la clase LocalDateTime
                .fechaCreacion(LocalDateTime.now())
                //Seteamos el Boolean leido a false
                .leido(false)
                .build();
        notificacionRepository.save(notificacion);

        /***NOTIFICACIÓN POR MAIL***/
        //Validamos que el usuario tenga un mail asociado (Que sea distinto de null o que no esté vacío
        if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
            try {
                //Llamamos al método enviar correo del service de email
                emailService.enviarCorreo(usuario.getEmail(), "Nueva Notificación", mensaje);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return notificacion;
    }



    //Método para obtener notificaciones por ID de usuario
    @Override
    public List<Notificacion> obtenerNotificacionesPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId);
    }

    //Método para marcar notificación como leída
    public void marcarComoLeida(Long notificacionId) {
        //Buscamos la notificación por id
        Notificacion notificacion = notificacionRepository.findById(notificacionId).get();
        //Seteamos leído como true
        notificacion.setLeido(true);
    }

}
