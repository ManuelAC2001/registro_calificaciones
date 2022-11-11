package control_calificaciones.models;

public class Tutor {

    private Integer id_tutor;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;

    public Integer getId_tutor() {
        return id_tutor;
    }

    public void setId_tutor(Integer id_tutor) {
        this.id_tutor = id_tutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    @Override
    public String toString() {
        return "Tutor [id_tutor=" + id_tutor + ", nombre=" + nombre + ", apellido_paterno=" + apellido_paterno
                + ", apellido_materno=" + apellido_materno + "]";
    }
}
