package control_calificaciones.models;

import java.sql.Date;
import java.time.LocalDate;

public class Alumno {

    private String curp;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String nombreCompleto;
    private Date fecha_nacimiento;
    private Integer edad;
    private Character genero;
    private Integer id_tutor;

    private Integer id_aula;
    
    // info de aula
    private String nombre_grado;
    private String nombre_grupo;

    public String getNombreCompleto() {
        nombreCompleto = nombre + " " + apellido_paterno + " " + apellido_materno; 
        return nombreCompleto;
    }

    public String getNombre_grado() {
        return nombre_grado;
    }

    public void setNombre_grado(String nombre_grado) {
        this.nombre_grado = nombre_grado;
    }

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
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

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }

    public Integer getId_tutor() {
        return id_tutor;
    }

    public void setId_tutor(Integer id_tutor) {
        this.id_tutor = id_tutor;
    }

    public Integer getId_aula() {
        return id_aula;
    }

    public void setId_aula(Integer id_aula) {
        this.id_aula = id_aula;
    }

    public Integer getEdad() {

        Integer anioNacimiento = fecha_nacimiento.toLocalDate().getYear();
        Integer anioActual = LocalDate.now().getYear();

        edad = anioActual - anioNacimiento;
        return edad;
    }

    @Override
    public String toString() {
        return "Alumno [curp=" + curp + ", nombre=" + nombre + ", apellido_paterno=" + apellido_paterno
                + ", apellido_materno=" + apellido_materno + ", fecha_nacimiento=" + fecha_nacimiento + ", edad="
                + getEdad()
                + ", genero=" + genero + ", id_tutor=" + id_tutor + ", id_aula=" + id_aula + ", nombre_grado="
                + nombre_grado + ", nombre_grupo=" + nombre_grupo + "]";
    }

}
