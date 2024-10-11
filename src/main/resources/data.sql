-- Puedo crear un archivo data.sql o schema.sql para que JPA lo inicialice en conjunto con la aplicación

INSERT IGNORE INTO usuarios (nombre, apellido, email, password, tipo_usuario) VALUES
    ("Doctor1", "Doctor1", "doctor1@mail.com", "doctor1" , "DOCTOR"),
    ("Doctor2", "Doctor2", "doctor2@mail.com", "doctor2", "DOCTOR"),
    ("Paciente1", "Paciente1", "paciente1@mail.com", "paciente1", "PACIENTE"),
    ("Paciente2", "Paciente2", "paciente2@mail.com", "paciente2", "PACIENTE");



INSERT IGNORE INTO doctores (especialidad, atencion_online, usuario_id) VALUES
    ("Cardiologia", true, 1),
    ("Neurología", false, 2);


INSERT IGNORE INTO pacientes (motivo_consulta, usuario_id) VALUES
    ("Le rompieron el corazón", 5),
    ("Le duele el pelo", 6);



