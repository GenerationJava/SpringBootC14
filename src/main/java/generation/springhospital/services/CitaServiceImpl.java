package generation.springhospital.services;

import generation.springhospital.models.Cita;
import generation.springhospital.repositories.CitaRepository;
import generation.springhospital.repositories.DoctorRepository;
import generation.springhospital.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

//Anotaciones
@Service
public class CitaServiceImpl implements CitaService {

    //Inyección de dependencias
    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private HorarioServiceImpl horarioServiceImpl;

    //Método para agendar cita
    @Override
    public Cita agendarCita(Long doctorId, Long pacienteId, LocalDate fecha, LocalTime hora) {
        //1-) Validar si la hora esta contenida dentro del intervalo y guardamos el resultado en una variable
        boolean esValida = horarioServiceImpl.validarCitaDentroDelHorario(doctorId, fecha, hora);

        if (!esValida) {
            throw new IllegalArgumentException("La hora indicada no está disponible");
        }

        //2-) Validar que no exista otra cita con el doctor a la misma hora
        boolean existeCita = citaRepository.existsByDoctorIdAndFechaAndHora(doctorId, fecha, hora);

        if (existeCita) {
            throw new IllegalArgumentException("La hora indicada ya está ocupada");
        }

        //3-) Generar la cita y guardar
        Cita nuevaCita = Cita.builder()
                        .doctor(doctorRepository.findById(doctorId).get())
                        .paciente(pacienteRepository.findById(pacienteId).get())
                        .fecha(fecha)
                        .hora(hora)
                        .build();

        citaRepository.save(nuevaCita);
        return nuevaCita;
    }



}
