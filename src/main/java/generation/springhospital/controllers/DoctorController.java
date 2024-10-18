package generation.springhospital.controllers;

import generation.springhospital.models.Doctor;
import generation.springhospital.models.Usuario;
import generation.springhospital.services.DoctorServiceImpl;
import generation.springhospital.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controladores van a controlar vistas
@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorServiceImpl;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    //Método GET para mostrar una vista llamada indexdoctores al ingresar a la ruta "localhost:8080/doctor/index"
    @GetMapping("/lista")
    public String indexDoctores(Model model) {
        List<Doctor> doctores = doctorServiceImpl.findAll();
        model.addAttribute("doctores", doctores);
        return "lista-doctores.html";
    }


    @GetMapping("/crear")
    public String mostrarFormulario(Model model, @RequestParam Long usuarioId){  // el @RequestParam sirve para traer un valor dinámico desde la ruta anterior
        // creamos una variable usuario y la inicializamos con el usuario según su id
        Usuario usuario = usuarioService.findById(usuarioId);
        // creamos una instancia de doctor vacía
        Doctor doctor = new Doctor();
        // a nuestra instancia de doctor le seteamos el usuario
        doctor.setUsuario(usuario);
        // a la vista (model) le pasamos la instancia de tipo doctor llamado "doctor"
        model.addAttribute("doctor", doctor);
        // retornamos el nombre de la vista, el archivo html
        return "registro-doctor";
    }

    @PostMapping("/crear")
    public String guardarDoctorCreado(@ModelAttribute Doctor doctor){  // al recibir atributos se usa el @ModelAttribute
        doctorServiceImpl.saveDoctor(doctor);
        return "redirect:/doctor/lista";
    }

}
