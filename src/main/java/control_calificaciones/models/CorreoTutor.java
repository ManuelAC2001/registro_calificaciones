package control_calificaciones.models;

public class CorreoTutor {

    private Integer id_correo;
    private String correo;
    private Integer id_tutor;

    public Integer getId_correo() {
        return id_correo;
    }
    public void setId_correo(Integer id_correo) {
        this.id_correo = id_correo;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public Integer getId_tutor() {
        return id_tutor;
    }
    public void setId_tutor(Integer id_tutor) {
        this.id_tutor = id_tutor;
    }

    @Override
    public String toString() {
        return "CorreoTutor [id_correo=" + id_correo + ", correo=" + correo + ", id_tutor=" + id_tutor + "]";
    }

}
