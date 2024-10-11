package generation.springhospital.api;

import generation.springhospital.models.Cita;
import generation.springhospital.services.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalTime;

//Anotaciones
@RestController
@RequestMapping("/api/citas")
public class CitaRestController {



    /** INYECCIÓN DE DEPENDENCIAS **/
    @Autowired
    private CitaService citaService;




    /** GENERAR NUEVA CITA CON EL ID DEL DOCTOR Y EL ID DEL PACIENTE **/
    //Método post para generar la nueva cita
    @PostMapping("/{doctorId}/{pacienteId}")
    public ResponseEntity<Cita> agendarCita(@PathVariable Long doctorId,
                                            @PathVariable Long pacienteId,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
                                            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime hora ) {

        //Creacion de las notificaciones para doctor y paciente

        //Llamado al servicio de envío de mail

        return new ResponseEntity<>(citaService.agendarCita(doctorId, pacienteId, fecha, hora), HttpStatus.OK);

    }




}
