package generation.springhospital.api;

import generation.springhospital.models.Cita;
import generation.springhospital.services.CitaServiceImpl;
import generation.springhospital.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

//Anotaciones
@RestController
@RequestMapping("/api/citas")
public class CitaRestController {

    /** INYECCIÓN DE DEPENDENCIAS **/
    @Autowired
    private CitaServiceImpl citaService;

    @Autowired
    private NotificacionService notificacionService;




    /** GENERAR NUEVA CITA CON EL ID DEL DOCTOR Y EL ID DEL PACIENTE **/
    //Método post para generar la nueva cita
    @PostMapping("/{doctorId}/{pacienteId}")
    public ResponseEntity<Cita> agendarCita(@PathVariable Long doctorId,
                                            @PathVariable Long pacienteId,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
                                            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime hora ) {

        //Llamado al servicio de envío de mail
        Cita nuevaCita = citaService.agendarCita(doctorId, pacienteId, fecha, hora);


        //Creacion de las notificaciones para doctor y paciente
        notificacionService.crearNotificacion(
                nuevaCita.getPaciente(), "Tu cita ha sido agendada para el " +
                        nuevaCita.getFecha() + " a las " + nuevaCita.getHora());

        notificacionService.crearNotificacion((
                nuevaCita.getDoctor()), "Tienes una nueva cita con " +
                nuevaCita.getPaciente().getNombre() + " el " + nuevaCita.getFecha() + " a las " +
                nuevaCita.getHora());



        return new ResponseEntity<>(citaService.agendarCita(doctorId, pacienteId, fecha, hora), HttpStatus.OK);

    }




}
