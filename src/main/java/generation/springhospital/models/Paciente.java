package generation.springhospital.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DiscriminatorValue("PACIENTE")
public class Paciente extends Usuario {

    private String motivoConsulta;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /****************ESPACIO PARA OTROS ATRIBUTOS******************/
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /************************************************************/



}
