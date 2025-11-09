package model;

public class Estudio {
    private int idEstudio;
    private int idFuncionario;
    private String universidad;
    private String nivelEstudio;
    private String titulo;

    // Constructor vacío
    public Estudio() {
    }

    // Constructor completo
    public Estudio(int idEstudio, int idFuncionario, String universidad,
            String nivelEstudio, String titulo) {
        this.idEstudio = idEstudio;
        this.idFuncionario = idFuncionario;
        this.universidad = universidad;
        this.nivelEstudio = nivelEstudio;
        this.titulo = titulo;
    }

    // Constructor sin ID (para insertar nuevos registros)
    public Estudio(int idFuncionario, String universidad, String nivelEstudio, String titulo) {
        this.idFuncionario = idFuncionario;
        this.universidad = universidad;
        this.nivelEstudio = nivelEstudio;
        this.titulo = titulo;
    }

    // Getters y Setters
    public int getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(int idEstudio) {
        this.idEstudio = idEstudio;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getNivelEstudio() {
        return nivelEstudio;
    }

    public void setNivelEstudio(String nivelEstudio) {
        this.nivelEstudio = nivelEstudio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Estudio{" +
                "idEstudio=" + idEstudio +
                ", idFuncionario=" + idFuncionario +
                ", universidad='" + universidad + '\'' +
                ", nivelEstudio='" + nivelEstudio + '\'' +
                ", titulo='" + titulo + '\'' +
                '}';
    }
}
