package generation.springhospital.services;

import generation.springhospital.models.Horario;
import generation.springhospital.repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    //Método para buscar horario por id
    public Horario findById(Long id) {
        return horarioRepository.findById(id).get();
    }

    //Método para buscar todos los horarios
    public List<Horario> findAll() {
        return horarioRepository.findAll();
    }

    //Método para crear un nuevo horario
    public Horario saveHorario(Horario horarioNuevo) {
        return horarioRepository.save(horarioNuevo);
    }

    //Método para borrar horario por id
    public void deleteHorarioById(Long id) {
        horarioRepository.deleteById(id);
    }

    //Método para buscar horarios por el estado
    public List<Horario> findHorarioByEstado(String estado) {
        return horarioRepository.findByEstado(estado);
    }

    //Método para generar intervalos de una hora a partir de un horario



    //Método para obtener la lista de intervalos disponibles



    //Método para validar que una cita pueda ser agendada dentro de un horario



}
