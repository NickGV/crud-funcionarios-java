CREATE DATABASE IF NOT EXISTS talento_humano;
USE talento_humano;

CREATE TABLE funcionario (
    id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    tipo_identificacion VARCHAR(20),
    numero_identificacion VARCHAR(20) UNIQUE,
    nombres VARCHAR(50),
    apellidos VARCHAR(50),
    estado_civil VARCHAR(20),
    sexo CHAR(1),
    direccion VARCHAR(100),
    telefono VARCHAR(20),
    fecha_nacimiento DATE
);

CREATE TABLE grupo_familiar (
    id_familiar INT AUTO_INCREMENT PRIMARY KEY,
    id_funcionario INT,
    nombre VARCHAR(50),
    parentesco VARCHAR(30),
    edad INT,
    telefono VARCHAR(20),
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario)
        ON DELETE CASCADE
);

CREATE TABLE estudio (
    id_estudio INT AUTO_INCREMENT PRIMARY KEY,
    id_funcionario INT,
    universidad VARCHAR(100),
    nivel_estudio VARCHAR(50),
    titulo VARCHAR(100),
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario)
        ON DELETE CASCADE
);

INSERT INTO funcionario (tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento)
VALUES
('CC', '1001234567', 'Carlos', 'Pérez López', 'Soltero', 'M', 'Calle 45 #12-23', '3001234567', '1990-05-12'),
('CC', '1009876543', 'María', 'Gómez Ruiz', 'Casada', 'F', 'Carrera 10 #8-20', '3109876543', '1988-09-30');

INSERT INTO grupo_familiar (id_funcionario, nombre, parentesco, edad, telefono)
VALUES
(1, 'Ana López', 'Madre', 55, '3005557788'),
(1, 'Juan Pérez', 'Hermano', 28, '3012233445'),
(2, 'Carlos Martínez', 'Esposo', 36, '3009998877');

INSERT INTO estudio (id_funcionario, universidad, nivel_estudio, titulo)
VALUES
(1, 'Universidad de Antioquia', 'Pregrado', 'Ingeniería de Sistemas'),
(1, 'Instituto CESDE', 'Técnico', 'Programación de Software'),
(2, 'Universidad Nacional', 'Maestría', 'Gestión de Proyectos de TI');
