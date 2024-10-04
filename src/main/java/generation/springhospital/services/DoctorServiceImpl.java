package generation.springhospital.services;

import generation.springhospital.models.Doctor;
import generation.springhospital.repositories.DoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    //Inyección de dependencias
    @Autowired
    private DoctorRepository doctorRepository;


    @Override
    public Doctor findById(Long id) {
        //Llama al repository y al método para buscar por ID
        return doctorRepository.getReferenceById(id);
    }

    @Transactional
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }


}
