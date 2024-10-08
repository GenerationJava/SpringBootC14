package generation.springhospital.api;

import generation.springhospital.models.Doctor;
import generation.springhospital.services.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctores")
public class DoctorRestController {

    @Autowired
    private DoctorServiceImpl doctorServiceImpl;

    @GetMapping("/doctor")
    public Doctor findDoctorById(@RequestParam Long id) {
        Doctor doctorSeleccionado = doctorServiceImpl.findById(id);
        System.out.println(doctorSeleccionado.getEspecialidad());
        return doctorSeleccionado;
    }


}
