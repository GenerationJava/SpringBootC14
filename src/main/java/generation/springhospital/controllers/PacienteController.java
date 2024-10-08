package generation.springhospital.controllers;

import generation.springhospital.models.Paciente;
import generation.springhospital.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/lista")
    public List<Paciente> listarPacientes() {
        System.out.println(pacienteService.findAllPaciente());
        return pacienteService.findAllPaciente();
    }

}
