package generation.springhospital.services;

import generation.springhospital.models.Doctor;

import java.util.List;

public interface DoctorService {

    //Declaración de métodos
    Doctor findById(Long id);

    List<Doctor> findAll();

}
