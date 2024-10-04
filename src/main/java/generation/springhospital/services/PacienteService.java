package generation.springhospital.services;

import generation.springhospital.models.Paciente;
import generation.springhospital.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente findById(Long id) {
        return pacienteRepository.findById(id).get();
    }

    public Paciente savePaciente(Paciente nuevoPaciente) {
        return pacienteRepository.save(nuevoPaciente);
    }


}
