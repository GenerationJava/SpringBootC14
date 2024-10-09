package generation.springhospital.api;

import generation.springhospital.models.Doctor;
import generation.springhospital.models.Horario;
import generation.springhospital.services.DoctorService;
import generation.springhospital.services.DoctorServiceImpl;
import generation.springhospital.services.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioRestController {

    /** INYECCIÓN DE DEPENDENCIAS **/
    @Autowired
    private HorarioService horarioService;

    @Autowired
    private DoctorServiceImpl doctorService;

    /** OBTENER LISTA DE TODOS LOS HORARIOS CREADOS **/
    //Clase ResponseEntity<List<Horario>> permite manipular el status de la respuesta
    @GetMapping("/lista")
    public ResponseEntity<List<Horario>> findAllHorario() {
        List<Horario> listaHorarios = horarioService.findAll();
        //Retornamos una nueva instancia de ResponseEntity
        //return new ResponseEntity<>(horarioService.findAll(), HttpStatus.OK)
        return new ResponseEntity<>(listaHorarios, HttpStatus.OK);
    }

    /** OBTENER HORARIO POR ID **/
    //Al no conocer el tipo de dato que se va a retornar podemos indicar que se retorna un responseEntity<?>
    //@PathVariable = localhost/api/horarios/5
    @GetMapping("/{id}")
    public ResponseEntity<?> findHorarioById(@PathVariable Long id) {
        return new ResponseEntity<>(horarioService.findById(id), HttpStatus.OK);
    }


    /** OBTENER HORARIOS POR ESTADO **/
    @GetMapping
    public ResponseEntity<?> findHorarioByEstado(@RequestParam String estado) {
        return new ResponseEntity<>(horarioService.findHorarioByEstado(estado), HttpStatus.OK);
    }
    //RequestParam = localhost/api/horarios?estado=DISPONIBLE

    /** CREAR NUEVO HORARIO PARA DOCTOR CON EL ID DE DOCTOR **/
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

    /** EDITAR HORARIO POR ID **/
    //PUT es el método HTTP para trabajar con edición
    //ID del horario va a ser la variable o criterio de búsqueda del horario a editar
    //Va a recibir un objeto de tipo Horario con los campos editados
    @PutMapping("/editar/{id}")
    public ResponseEntity<Horario> updateHorarioById(@PathVariable Long id,
                                                     @RequestBody Horario horarioEditado) {
        //Buscamos primero, el horario por ID
        Horario horarioSeleccionado = horarioService.findById(id);

        //Al horarioEditado que viene en el cuerpo de la petición, le seteamos el ID de nuestro horarioSeleccionado
        horarioEditado.setId(horarioSeleccionado.getId());

        //Al horarioeEditado le seteamos el doctor del horarioSeleccionado
        horarioEditado.setDoctor(horarioSeleccionado.getDoctor());

        //Retornamos una nueva ResponseEntity pasando como argumento el método de guardar horario
        return new ResponseEntity<>(horarioService.saveHorario(horarioEditado), HttpStatus.OK);
    }


    /** ELIMINAR HORARIO POR ID **/
    //DELETE es el método HTTP que me permite eliminar registros
    //ID del horario va a ser el atributo por el que voy a filtrar
    //Vamos a recibir un dato de tipo Long que es el ID a través de la ruta
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> deleteHorarioById(@PathVariable Long id) {
        //LLamamos al método del service para borrar por ID
        horarioService.deleteHorarioById(id);
        //Retornamos un nuevo ResponseEntity indicando un mensaje en el cuerpo de la respuesta
        return new ResponseEntity<>("El horario ha sido eliminado", HttpStatus.OK);
    }



    /** GENERAR INTÉRVALOS DE UNA HORA A PARTIR DEL ID DEL DOCTOR Y FECHA DEL HORARIO CREADO **/
    @GetMapping("/disponibilidad/{doctorId}")
    public ResponseEntity<List<LocalTime>> findIntervalosByDoctorIdAndFecha(@PathVariable Long doctorId,
                                                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha) {
        //Creamos una variable para almacenar la lista
        List<LocalTime> intervalosDisponibles = horarioService.obtenerIntervalosDeUnaHora(doctorId, fecha);

        return new ResponseEntity<>(intervalosDisponibles, HttpStatus.OK);
    }

}
