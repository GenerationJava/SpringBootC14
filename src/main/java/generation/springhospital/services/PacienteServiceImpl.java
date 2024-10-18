package generation.springhospital.services;

import generation.springhospital.models.Paciente;
import generation.springhospital.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public Paciente findById(Long id) {
        return pacienteRepository.findById(id).get();
    }

    @Override
    public Paciente savePaciente(Paciente nuevoPaciente) {
        return pacienteRepository.save(nuevoPaciente);
    }

    @Override
    public List<Paciente> findAllPaciente() {
        //Llamado al meétodo para buscar List<Paciente> del repositorio
        return pacienteRepository.findAll();
    }


}
