package generation.springhospital.controllers;

import generation.springhospital.services.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//Controladores van a controlar vistas
@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorServiceImpl;

    //Método GET para mostrar una vista llamada indexdoctores al ingresar a la ruta "localhost:8080/doctor/index"
    @GetMapping("/lista")
    public String indexDoctores() {
        return "indexdoctores.html";
    }


}
