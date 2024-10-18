package generation.springhospital.api;

import generation.springhospital.models.Paciente;
import generation.springhospital.services.PacienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteRestController {

    @Autowired
    private PacienteServiceImpl pacienteServiceImpl;


    @GetMapping("/lista")
    public List<Paciente> listarPacientes() {
        return pacienteServiceImpl.findAllPaciente();
    }
}
