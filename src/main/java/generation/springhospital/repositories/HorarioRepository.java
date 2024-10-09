package generation.springhospital.repositories;

import generation.springhospital.models.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    //Jpa permite trabajar con consultas por algún atributo en específico
    //Estos métodos que funcionan con un atributo específico de una clase hay que declararlos
    List<Horario> findByEstado(String estado);

    //Declaro método para buscar Horario por el ID del doctor
    List<Horario> findByDoctorId(Long id);

    Horario findByDoctorIdAndFecha(Long id, LocalDate fecha);




}
