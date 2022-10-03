package control_calificaciones.data.usuarios;

import java.sql.*;
import java.util.ArrayList;

import control_calificaciones.data.Conexion;
import control_calificaciones.models.usuarios.*;

public class UsuarioDAO {
    
    //Esta funcion almacena la informacion de la BD a un Objeto de tipo usuario
    private void setInformacion(Usuario usuario, ResultSet rs) throws SQLException{
        usuario.idUsuario = rs.getInt("id_usuario");
        usuario.nombreUsuario = rs.getString("nombre_usuario");
        usuario.contrasenia = rs.getString("contrasenia");
        usuario.idRol = rs.getInt("id_rol");
        usuario.nombreRol = rs.getString("nombre_rol");
        usuario.privilegios = getPrivilegios(usuario);
    }

    public Usuario buscar(String nombreUsuario){
        
        Usuario usuarioEncontrado = new Usuario();
        String callProcedure = "{CALL buscarUsuario(?)}";
        
        Connection cn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(callProcedure);
            cstmt.setString(1, nombreUsuario);
            rs = cstmt.executeQuery();

            if(!rs.next()){
                return null;
            }
            setInformacion(usuarioEncontrado, rs);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            Conexion.close(rs);
            Conexion.close(cstmt);
            Conexion.close(cn);
        }

        return usuarioEncontrado;
    }

    public ArrayList<String> getPrivilegios(Usuario usuario){
        
        ArrayList<String> privilegios = new ArrayList<>();
        String callProcedure = "{CALL getPrivilegiosUsuario(?)}";
        
        Connection cn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(callProcedure);
            cstmt.setString(1, usuario.nombreUsuario);
            rs = cstmt.executeQuery();

            while(rs.next()){
                privilegios.add( rs.getString("nombre"));
            } 
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Conexion.close(rs);
            Conexion.close(cstmt);
            Conexion.close(cn);
        }
        return privilegios;
    }
}
