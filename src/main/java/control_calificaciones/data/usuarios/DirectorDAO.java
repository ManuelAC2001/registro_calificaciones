package control_calificaciones.data.usuarios;

import java.sql.*;

import control_calificaciones.data.Conexion;
import control_calificaciones.models.usuarios.Director;
import control_calificaciones.models.usuarios.Usuario;

public class DirectorDAO extends UsuarioDAO{
    
    public Usuario buscar(String nombreUsuario) {
        Director directorEncontrado = new Director();
        String callProcedure = "{CALL buscarDirector(?)}";
        
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
            setInformacion(directorEncontrado, rs);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            Conexion.close(rs);
            Conexion.close(cstmt);
            Conexion.close(cn);
        }

        return directorEncontrado;
        
    }

    public void insertarSecretaria(Usuario usuario){

        String procedureCall = "{CALL crearSecretaria(?,?,?)}";
        Connection cn = null;
        CallableStatement csmt = null;

        try {

            cn = Conexion.getConnection();
            csmt = cn.prepareCall(procedureCall);

            csmt.setString(1, usuario.nombreUsuario);
            csmt.setString(2, usuario.correo);
            csmt.setString(3, usuario.contrasenia);

            csmt.executeUpdate();
            System.out.println("agregado correctamente");

        } 
        catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        finally{
            Conexion.close(csmt);
            Conexion.close(cn);
        }



    }
}
