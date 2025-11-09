package model;

public class GrupoFamiliar {
    private int idFamiliar;
    private int idFuncionario;
    private String nombre;
    private String parentesco;
    private int edad;
    private String telefono;

    // Constructor vacío
    public GrupoFamiliar() {
    }

    // Constructor completo
    public GrupoFamiliar(int idFamiliar, int idFuncionario, String nombre,
            String parentesco, int edad, String telefono) {
        this.idFamiliar = idFamiliar;
        this.idFuncionario = idFuncionario;
        this.nombre = nombre;
        this.parentesco = parentesco;
        this.edad = edad;
        this.telefono = telefono;
    }

    // Constructor sin ID (para insertar nuevos registros)
    public GrupoFamiliar(int idFuncionario, String nombre, String parentesco,
            int edad, String telefono) {
        this.idFuncionario = idFuncionario;
        this.nombre = nombre;
        this.parentesco = parentesco;
        this.edad = edad;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getIdFamiliar() {
        return idFamiliar;
    }

    public void setIdFamiliar(int idFamiliar) {
        this.idFamiliar = idFamiliar;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "GrupoFamiliar{" +
                "idFamiliar=" + idFamiliar +
                ", idFuncionario=" + idFuncionario +
                ", nombre='" + nombre + '\'' +
                ", parentesco='" + parentesco + '\'' +
                ", edad=" + edad +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
