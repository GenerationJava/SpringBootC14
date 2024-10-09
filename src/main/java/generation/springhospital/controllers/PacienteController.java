package generation.springhospital.controllers;

import generation.springhospital.models.Paciente;
import generation.springhospital.models.Usuario;
import generation.springhospital.services.PacienteService;
import generation.springhospital.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/lista")
    public String listarPacientes(Model model) {
        List<Paciente> pacientes = pacienteService.findAllPaciente();
        model.addAttribute("pacientes", pacientes);
        System.out.println(pacienteService.findAllPaciente());
        return "lista-pacientes";
    }

    @GetMapping("/crear")
    public String mostrarFormularioPaciente(Model model, @RequestParam Long usuarioId){  // el @RequestParam sirve para traer un valor dinámico desde la ruta anterior
        // creamos una variable usuario y la inicializamos con el usuario según su id
        Usuario usuario = usuarioService.findById(usuarioId);
        // creamos una instancia de doctor vacía
        Paciente paciente = new Paciente();
        // a nuestra instancia de doctor le seteamos el usuario
        paciente.setUsuario(usuario);
        // a la vista (model) le pasamos la instancia de tipo doctor llamado "doctor"
        model.addAttribute("paciente", paciente);
        return "registro-paciente";
    }

    @PostMapping("/crear")
    public String guardarDoctorCreado(@ModelAttribute Paciente paciente){  // al recibir atributos se usa el @ModelAttribute
        pacienteService.savePaciente(paciente);
        return "redirect:/paciente/lista";
    }


}
