package generation.springhospital.services;

import generation.springhospital.models.Horario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface HorarioService {

    Horario findById(Long id);

    List<Horario> findAll();

    Horario saveHorario(Horario horarioNuevo);

    void deleteHorarioById(Long id);

    List<Horario> findHorarioByEstado(String estado);

    List<LocalTime> obtenerIntervalosDeUnaHora(Long doctorId, LocalDate fecha);


}
