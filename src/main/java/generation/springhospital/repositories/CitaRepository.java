package generation.springhospital.repositories;

import generation.springhospital.models.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

//Anotaciones
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    //Declaramos método para verificar si existe otra cita con el mismo doctor a la misma hora
    boolean existsByDoctorIdAndFechaAndHora(Long doctorId, LocalDate fecha, LocalTime hora);

    //Crear métodos que trabajen con querys nativas, con SQL
    /**@Query(value = "select * from citas c where c.doctor_id = 1?", nativeQuery = true)
    @Query(value = "select * from citas c where c.doctor_id = :doctorId and c.fecha = :fecha", nativeQuery = true)
    List<Cita> findAllCitaByDoctorId(@Param("doctorId") Long doctorId, @Param("fecha") LocalDate fecha);**/

    //JPQL
}
