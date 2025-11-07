package model;

import java.util.Date;

public class Funcionario {
    private int id;
    private String tipoDocumento;
    private String numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private String estadoCivil;
    private String sexo;
    private String direccion;
    private String telefono;
    private Date fechaNacimiento;

    // Constructor vacío
    public Funcionario() {}

    // Constructor con parámetros
    public Funcionario(int id, String tipoDocumento, String numeroIdentificacion, String nombres,
                       String apellidos, String estadoCivil, String sexo, String direccion,
                       String telefono, Date fechaNacimiento) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y setters
    // ...
}
