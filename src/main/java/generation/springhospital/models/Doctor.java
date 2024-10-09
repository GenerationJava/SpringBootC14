package generation.springhospital.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "doctores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String especialidad;

    private Boolean atencionOnline;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /*************ESPACIO PARA OTROS ATRIBUTOS******************/
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Horario> horarios;

    @ManyToMany
    @JoinTable(//Al tener una relacion de N a N, indicamos nombre de la tabla relacional
            name = "especialidades_doctores",
            joinColumns = @JoinColumn(name = "doctor_id"),//Nombre de la columna que lleva la llave foránea
            inverseJoinColumns = @JoinColumn(name = "especialidad_id"))//Nombre de la columna de  la otra entidad
    private List<Especialidad> especialidades;

    /***********************************************************/

    /*
    public Doctor() {
    }

    public Doctor(String especialidad, Boolean atencionOnline) {
        this.especialidad = especialidad;
        this.atencionOnline = atencionOnline;
    }

    public long getId() {
        return id;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public Boolean getAtencionOnline() {
        return atencionOnline;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setAtencionOnline(Boolean atencionOnline) {
        this.atencionOnline = atencionOnline;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", especialidad='" + especialidad + '\'' +
                ", atencionOnline=" + atencionOnline +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }*/
}
