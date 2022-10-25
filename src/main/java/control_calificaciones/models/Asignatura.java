package control_calificaciones.models;

public class Asignatura {

    private Integer id_asignatura;
    private String nombre_materia;
    private Integer id_grado;
    private String nombre_grado;
    private Integer id_tipo;
    private String tipo;

    public Integer getId_asignatura() {
        return id_asignatura;
    }
    public void setId_asignatura(Integer id_asignatura) {
        this.id_asignatura = id_asignatura;
    }
    public String getNombre_materia() {
        return nombre_materia;
    }
    public void setNombre_materia(String nombre_materia) {
        this.nombre_materia = nombre_materia;
    }
    public Integer getId_grado() {
        return id_grado;
    }
    public void setId_grado(Integer id_grado) {
        this.id_grado = id_grado;
    }
    public String getNombre_grado() {
        return nombre_grado;
    }
    public void setNombre_grado(String nombre_grado) {
        this.nombre_grado = nombre_grado;
    }
    public Integer getId_tipo() {
        return id_tipo;
    }
    public void setId_tipo(Integer id_tipo) {
        this.id_tipo = id_tipo;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Asignaturas [id_asignatura=" + id_asignatura + ", nombre_materia=" + nombre_materia + ", id_grado="
                + id_grado + ", nombre_grado=" + nombre_grado + ", id_tipo=" + id_tipo + ", tipo=" + tipo + "]";
    }

        
}
