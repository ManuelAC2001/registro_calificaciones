package control_calificaciones.models;

public class BitacoraSesion {
    
    private Integer anio;
    private Integer dia;

    
    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    private String horaEntrada;
    private String horaSalida;
    private String mes;
    private String nombreUsuario;



    public Integer getAnio(){
        return this.anio; 
    }

    public void setAnio(Integer anio){
        this.anio = anio;
    }
    
}
