package generation.springhospital.repositories;

import generation.springhospital.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//Spring va a tomar esta interfaz como un componente que puedo luego inyectar en otras capas
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    //Los repositorios son interfaces porque sólo definen métodos
    /**La clase JpaRepository, contiene métodos para hacer el CRUD**/


}
