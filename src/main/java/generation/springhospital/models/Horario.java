package generation.springhospital.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "horarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Horario {
    //Entidad para definir los horarios disponibles del doctor
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaInicio;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaFin;

    @Enumerated(EnumType.STRING)
    private EstadoHorario estado;

    public enum EstadoHorario {
        DISPONIBLE,
        OCUPADO
    }

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne//Many to one permite crear una relación de uno a muchos (1 a n)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;


    /*
    public Horario() {
    }

    public Horario(LocalTime horaInicio, LocalTime horaFin, Doctor doctor) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.doctor = doctor;
    }

    public long getId() {
        return id;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }*/
}
