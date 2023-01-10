package control_calificaciones.models;

public class Aula {

    private Integer id_aula;
    private Integer id_grado;
    private String nombre_grado;
    private Integer id_grupo;
    private String nombre_grupo;
    private Integer cantidad;

    public Integer getId_aula() {
        return id_aula;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setId_aula(Integer id_aula) {
        this.id_aula = id_aula;
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

    public Integer getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(Integer id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }

    @Override
    public String toString() {
        return "Aula [id_aula=" + id_aula + ", id_grado=" + id_grado + ", nombre_grado=" + nombre_grado + ", id_grupo="
                + id_grupo + ", nombre_grupo=" + nombre_grupo + ", cantidad=" + cantidad + "]";
    }
}
