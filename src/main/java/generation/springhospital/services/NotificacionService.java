package generation.springhospital.services;

import generation.springhospital.models.Notificacion;
import generation.springhospital.models.Usuario;

import java.util.List;

public interface NotificacionService {

    Notificacion crearNotificacion(Usuario usuario, String mensaje);

    List<Notificacion> obtenerNotificacionesPorUsuario(Long usuarioId);

    void marcarComoLeida(Long notificacionId);
}
