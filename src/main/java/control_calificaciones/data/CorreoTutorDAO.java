package control_calificaciones.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import control_calificaciones.models.CorreoTutor;

public class CorreoTutorDAO {

    protected static void setInformacion(CorreoTutor correoTutor, ResultSet rs) throws SQLException{

        correoTutor.setId_correo( rs.getInt("id_correo") );
        correoTutor.setCorreo( rs.getString("correo") );
        correoTutor.setId_tutor( rs.getInt("id_tutor") );
    }
    
    public static CorreoTutor buscar(String correo){

        CorreoTutor correoTutor = new CorreoTutor();
        String callProcedure = "{CALL buscarCorreoTutor(?)}";

        Connection cn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(callProcedure);

            cstmt.setString(1, correo);
            rs = cstmt.executeQuery();

            if(!rs.next()){
                return null;
            }

            setInformacion(correoTutor, rs);

        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Conexion.close(rs);
            Conexion.close(cstmt);
            Conexion.close(cn);
        }
        return correoTutor;
    }


    public static boolean esRepetido(String correo){
        return buscar(correo) != null;
    }
}
