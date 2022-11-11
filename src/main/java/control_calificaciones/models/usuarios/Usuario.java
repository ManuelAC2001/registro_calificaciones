package control_calificaciones.models.usuarios;

import java.util.ArrayList;
public class Usuario {
    public int idUsuario;
    public String nombreUsuario;
    public String correo;
    public String contrasenia;
    public int idRol;
    public String nombreRol;
    public ArrayList<String> privilegios = new ArrayList<>();
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id: " + this.idUsuario + ", " +
                "nombre de usuario: " + this.nombreUsuario + ", " +
                "idRol: " + this.idRol + ", " +
                "nombre del rol: " + this.nombreRol + 
                "\nprivilegios: " + this.privilegios +
                "}";
    }
}
