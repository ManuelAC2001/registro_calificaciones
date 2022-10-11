package control_calificaciones.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import control_calificaciones.models.CorreoTutor;
import control_calificaciones.models.Tutor;

public class TutorDAO {

    protected void setInformacion(Tutor tutor, ResultSet rs) throws SQLException {
        tutor.setId_tutor(rs.getInt("id_tutor"));
        tutor.setNombre(rs.getString("nombre"));
        tutor.setApellido_paterno(rs.getString("apellido_paterno"));
        tutor.setApellido_materno(rs.getString("apellido_materno"));
    }

    public Tutor buscarByNombreCompleto(Tutor tutor) {

        Tutor tutorEncontrado = new Tutor();
        String callProcedure = "{CALL getTutorByNombreCompleto(?, ?, ?)}";

        Connection cn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(callProcedure);
            cstmt.setString(1, tutor.getNombre());
            cstmt.setString(2, tutor.getApellido_paterno());
            cstmt.setString(3, tutor.getApellido_materno());

            rs = cstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            // establecemos los datos del tutor
            setInformacion(tutorEncontrado, rs);

        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            Conexion.close(rs);
            Conexion.close(cstmt);
            Conexion.close(cn);
        }
        return tutorEncontrado;
    }

    public void insertar(Tutor tutor){

        String procedureCall = "{ CALL insertTutor(?, ?, ?) }";
        Connection cn = null;
        CallableStatement csmt = null;

        try {
            cn = Conexion.getConnection();
            csmt = cn.prepareCall(procedureCall);

            //establecemos los datos del procedimiento almacenado
            csmt.setString(1, tutor.getNombre());
            csmt.setString(2, tutor.getApellido_paterno());
            csmt.setString(3, tutor.getApellido_materno());

            csmt.executeQuery();
            System.out.println("agregado correctamente");
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Conexion.close(csmt);
            Conexion.close(cn);
        }

    }

    public ArrayList<CorreoTutor> getCorreos(Tutor tutor){

        ArrayList<CorreoTutor> correos = new ArrayList<>();
        String callProcedure = "{CALL getTutorCorreosById(?)}";

        Connection cn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        
        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(callProcedure);

            cstmt.setInt(1, tutor.getId_tutor());

            rs = cstmt.executeQuery();

            while(rs.next()){
                CorreoTutor correo = new CorreoTutor();
                correo.setId_correo( rs.getInt("id_correo") );
                correo.setCorreo( rs.getString("correo"));
                correo.setId_tutor( rs.getInt("id_tutor") );

                correos.add(correo);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Conexion.close(rs);
            Conexion.close(cstmt);
            Conexion.close(rs);
        }
        return correos;
    }

    public void insertarCorreo(Tutor tutor, String correo){

        String procedureCall = "{ CALL insertarCorreoTutor(?, ?) }";
        Connection cn = null;
        CallableStatement csmt = null;

        try {
            cn = Conexion.getConnection();
            csmt = cn.prepareCall(procedureCall);

            csmt.setString(1, correo);
            csmt.setInt(2, tutor.getId_tutor());

            csmt.executeQuery();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Conexion.close(csmt);
            Conexion.close(cn);
        }


    }

    
}
