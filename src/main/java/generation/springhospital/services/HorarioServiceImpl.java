package generation.springhospital.services;

import generation.springhospital.models.Horario;
import generation.springhospital.repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HorarioServiceImpl implements HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private DoctorServiceImpl doctorService;


    //Método para buscar horario por id
    @Override
    public Horario findById(Long id) {
        return horarioRepository.findById(id).get();
    }

    //Método para buscar todos los horarios
    @Override
    public List<Horario> findAll() {
        return horarioRepository.findAll();
    }

    //Método para crear un nuevo horario
    @Override
    public Horario saveHorario(Horario horarioNuevo) {
        return horarioRepository.save(horarioNuevo);
    }

    //Método para borrar horario por id
    @Override
    public void deleteHorarioById(Long id) {
        horarioRepository.deleteById(id);
    }

    //Método para buscar horarios por el estado
    @Override
    public List<Horario> findHorarioByEstado(String estado) {
        return horarioRepository.findByEstado(estado);
    }

    //Método para generar intervalos de una hora a partir de un horario
    @Override
    public List<LocalTime> obtenerIntervalosDeUnaHora(Long doctorId, LocalDate fecha) {
        //Horario creado lo guardamos en una variable
        Horario horario = horarioRepository.findByDoctorIdAndFecha(doctorId, fecha);

        if (horario == null) {
            return new ArrayList<>();//Si no existe el horario retorna una lista vacía
        }

        //Si existe el horario se generan los intervalos  de una hora
        List<LocalTime> intervalosUnaHora = generarIntervalos(horario.getHoraInicio(), horario.getHoraFin());

        return intervalosUnaHora;
    }


    //Método para obtener la lista de intervalos disponibles
    private List<LocalTime> generarIntervalos(LocalTime horaInicio, LocalTime horaFin) {
        //Generamos una variable como una lista de Horarios vacía
        List<LocalTime> intervalos = new ArrayList<>();

        //Generamos una variable que toma como referencia la hora de inicio, para el bucle
        LocalTime horaActual = horaInicio;

        //Generamos un bucle que toma la hora de inicio y la compara con la hora final
        //hasta que la hora alcance el mismo valor
        while (horaActual.isBefore(horaFin)) {
            //Le añadimos cada hora a la variable intervalos
            intervalos.add(horaActual);
            //A la horaActual le vamos a ir sumando una hora con cada iteración
            horaActual = horaActual.plusHours(1);
        }

        return intervalos;
    }


    //Método para validar que una cita pueda ser agendada dentro de un horario y con el mismo doctor
    public boolean validarCitaDentroDelHorario(Long doctorId, LocalDate fecha, LocalTime hora) {
        //Obtenemos los intervalos de una hora para el día en que se va a agendar
        List<LocalTime> intervalosDisponibles = obtenerIntervalosDeUnaHora(doctorId, fecha);
        //Retornamos un bolean si es que la hora está contenida en el intervalo
        return intervalosDisponibles.contains(hora);
    }



}
