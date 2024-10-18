package generation.springhospital.api;


import generation.springhospital.models.Notificacion;
import generation.springhospital.services.NotificacionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Anotaciones
@RestController
@RequestMapping("api/notificaciones")
public class NotificacionRestController {


    /** INYECCIÓN DE DEPENDENCIAS **/
    @Autowired
    private NotificacionServiceImpl notificacionService;


    /** OBTENER TODAS LAS NOTIFICACIONES POR ID **/
    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Notificacion>> obtenerNotificaciones(@PathVariable Long usuarioId) {
        List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesPorUsuario(usuarioId);
        return ResponseEntity.ok(notificaciones);
    }


    /** MARCAR NOTIFICACIÓN COMO LEÍDA **/
    @PutMapping("/{notificacionId}/leido")
    public ResponseEntity<Void> marcarNotificacionComoLeida(@PathVariable Long notificacionId) {
        notificacionService.marcarComoLeida(notificacionId);
        return ResponseEntity.ok().build();
    }


}
