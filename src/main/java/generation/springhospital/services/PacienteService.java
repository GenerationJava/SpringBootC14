package generation.springhospital.services;

import generation.springhospital.models.Paciente;

import java.util.List;

public interface PacienteService {

    Paciente findById(Long id);

    Paciente savePaciente(Paciente nuevoPaciente);

    List<Paciente> findAllPaciente();
}
