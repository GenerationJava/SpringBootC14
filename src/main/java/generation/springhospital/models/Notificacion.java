package generation.springhospital.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

//Anotaciones
@Entity
@Table(name = "notificaciones")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Notificacion {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Relación con la entidad Usuario

    private String mensaje;

    private LocalDateTime fechaCreacion;

    private boolean leido; // Campo para saber si la notificación fue leída

    // Getters y Setters


}
