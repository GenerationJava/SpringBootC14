package generation.springhospital.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)//Restricciones para la columna, no permitir null y sólo registros únicos
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)//Anotación para indicar que el valor del atributo va a tomarse de una enumeración
    @Column(nullable = false)
    private TipoUsuario tipoUsuario;

    //Atributos de auditoría, me permiten conocer la fecha de creación y de la última edición
    @CreationTimestamp//Anotación para generar el almacenamiento de la fecha de creación
    private LocalDateTime createdAt;

    @UpdateTimestamp//Anotación para generar el almacenamiento de la fecha de actualización
    private LocalDateTime updatedAt;

    /******ESPACIO PARA OTROS ATRIBUTOS*******/
    //Relaciones en Java se indican mediante anotaciones para que Hibernate cree las llaves foráneas en las tablas correspondientes
    @JsonIgnore
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)//Relación de 1 a 1
    private Doctor doctor;


    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Paciente paciente;


    /*****************************************/
    public Usuario() {
    }

    public Usuario(String email, String password, TipoUsuario tipoUsuario) {
        this.email = email;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
