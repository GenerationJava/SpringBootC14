package generation.springhospital.api;

import generation.springhospital.models.Doctor;
import generation.springhospital.models.Horario;
import generation.springhospital.services.DoctorService;
import generation.springhospital.services.DoctorServiceImpl;
import generation.springhospital.services.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioRestController {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private DoctorServiceImpl doctorService;

    //Clase ResponseEntity<List<Horario>> permite manipular el status de la respuesta
    @GetMapping("/lista")
    public ResponseEntity<List<Horario>> findAllHorario() {
        List<Horario> listaHorarios = horarioService.findAll();
        //Retornamos una nueva instancia de ResponseEntity
        //return new ResponseEntity<>(horarioService.findAll(), HttpStatus.OK)
        return new ResponseEntity<>(listaHorarios, HttpStatus.OK);
    }


    //Al no conocer el tipo de dato que se va a retornar podemos indicar que se retorna un responseEntity<?>
    @GetMapping("/{id}")
    public ResponseEntity<?> findHorarioById(@PathVariable Long id) {
        return new ResponseEntity<>(horarioService.findById(id), HttpStatus.OK);
    }
    //PathVariable = localhost/api/horarios/5

    @GetMapping
    public ResponseEntity<?> findHorarioByEstado(@RequestParam String estado) {
        return new ResponseEntity<>(horarioService.findHorarioByEstado(estado), HttpStatus.OK);
    }
    //RequestParam = localhost/api/horarios?estado=DISPONIBLE

    @PostMapping("/nuevo/{doctorId}")
    public ResponseEntity<?> saveHorarioNuevo(@RequestBody Horario horarioNuevo,
                                              @PathVariable Long doctorId) {
        //BUscamos al doctor por su id y lo guardamos en una variable
        Doctor doctorSeleccionado = doctorService.findById(doctorId);

        //Al horario que se está enviando en la petición le seteamos el doctor con la variable creada
        horarioNuevo.setDoctor(doctorSeleccionado);

        //Finalmente, guardamos el horario llamando al método en el service
        horarioService.saveHorario(horarioNuevo);
        return new ResponseEntity<>("El horario se ha creado exitosamente", HttpStatus.CREATED);
    }








}
