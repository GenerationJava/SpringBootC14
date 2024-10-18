package generation.springhospital.services;

import generation.springhospital.models.Cita;

import java.time.LocalDate;
import java.time.LocalTime;

public interface CitaService {

    Cita agendarCita(Long doctorId, Long pacienteId, LocalDate fecha, LocalTime hora);
}
