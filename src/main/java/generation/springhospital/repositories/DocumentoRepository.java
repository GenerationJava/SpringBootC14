package generation.springhospital.repositories;

import generation.springhospital.models.Documento;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Registered
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByPacienteId(Long pacienteId);
    List<Documento> findByCitaId(Long citaId);
}
