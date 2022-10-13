package control_calificaciones.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import control_calificaciones.models.Alumno;
import control_calificaciones.models.Aula;

public class AlumnoDAO {

    protected void setInformacion(Alumno alumno, ResultSet rs) throws SQLException{

        alumno.setCurp( rs.getString("curp"));
        alumno.setNombre( rs.getString("nombre") );
        alumno.setApellido_paterno( rs.getString("apellido_paterno") );
        alumno.setApellido_materno( rs.getString("apellido_materno") );
        alumno.setGenero( rs.getString("genero").charAt(0) );
        alumno.setId_aula( rs.getInt("id_aula") );
        alumno.setId_tutor( rs.getInt("id_tutor") );
        alumno.setFecha_nacimiento( rs.getDate("fecha_nacimiento") );

    }

    public Alumno buscar(String curp) {

        Alumno alumnoEncontrado = new Alumno();
        String callProcedure = "{CALL getAlumno(?)}";

        Connection cn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(callProcedure);
            cstmt.setString(1, curp);
            rs = cstmt.executeQuery();

            if(!rs.next()){
                return null;
            }

            //establecemos la informacion del alumno
            setInformacion(alumnoEncontrado, rs);

        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Conexion.close(rs);
            Conexion.close(cstmt);
            Conexion.close(cn);
        }

        return alumnoEncontrado;

    }

    public Alumno buscarByNombreCompleto(Alumno alumno){

        Alumno alumnoRepetido = new Alumno();
        String callProcedure = "{CALL getAlumnoByNombreCompleto(?, ?, ?)}";

        Connection cn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(callProcedure);
            cstmt.setString(1, alumno.getNombre());
            cstmt.setString(2, alumno.getApellido_paterno());
            cstmt.setString(3, alumno.getApellido_materno());
            rs = cstmt.executeQuery();

            if(!rs.next()){
                return null;
            }

            setInformacion(alumnoRepetido, rs);


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Conexion.close(rs);
            Conexion.close(cstmt);
            Conexion.close(cn);
        }


        return alumnoRepetido;


    }

    public void insertar(Alumno alumno){

        String procedureCall = "{CALL insertAlumno( ?, ?, ?, ?, ?, ?, ?, ?)}";
        Connection cn = null;
        CallableStatement csmt = null;

        try {
            cn = Conexion.getConnection();
            csmt = cn.prepareCall(procedureCall);

            //establecemos los datos del procedimiento almacenado
            csmt.setString( 1, alumno.getCurp());
            csmt.setString(2, alumno.getNombre());
            csmt.setString(3, alumno.getApellido_paterno());
            csmt.setString(4, alumno.getApellido_materno());
            csmt.setDate(5, alumno.getFecha_nacimiento());
            csmt.setString(6, alumno.getGenero().toString());
            csmt.setInt(7, alumno.getId_tutor());
            csmt.setInt(8, alumno.getId_aula());

            csmt.executeUpdate();
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

    public boolean esNombreRepetido(Alumno alumno){
        return buscarByNombreCompleto(alumno) != null;
    }

    public boolean esRepetido(Alumno alumno){
        return buscar(alumno.getCurp()) != null;
        // return buscarByNombreCompleto(alumno) != null;
    }

    public void eliminar(Alumno alumno){

        String procedureCall = "{CALL eliminarAlumnoByCurp(?)}";
        Connection cn = null;
        CallableStatement cstmt = null;

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(procedureCall);
            
            //le pasamos el parametro al procedimiento almacenado
            cstmt.setString(1, alumno.getCurp());
            cstmt.executeUpdate();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Conexion.close(cstmt);
            Conexion.close(cn);
        }

    }
    
    public void modificar(Alumno alumno){
        String procedureCall = "{CALL modificarAlumnoByCurp(?, ?, ?, ?, ?)}";
        Connection cn = null;
        CallableStatement cstmt = null;

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(procedureCall);

            cstmt.setString(1, alumno.getCurp());
            cstmt.setString(2, alumno.getNombre());
            cstmt.setString(3, alumno.getApellido_paterno());
            cstmt.setString(4, alumno.getApellido_materno());
            cstmt.setString(5, alumno.getGenero().toString());

            cstmt.executeUpdate();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Conexion.close(cstmt);
            Conexion.close(cn);
        }
    }

    public Aula getAula(Alumno alumno){

        Aula aula = new Aula(); 
        String callProcedure = "{CALL getAlumnoAula(?)}";

        Connection cn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getConnection();

            cstmt = cn.prepareCall(callProcedure);
            cstmt.setString(1, alumno.getCurp());
            rs = cstmt.executeQuery();

            if(!rs.next()){
                return null;
            }

            //establecemos la informacion del aula
            aula.setId_aula( rs.getInt("id_aula") );
            aula.setId_grado( rs.getInt("id_grado") );
            aula.setNombre_grado( rs.getString("nombre_grado") );
            aula.setId_grupo( rs.getInt("id_grupo") );
            aula.setNombre_grupo( rs.getString("nombre_grupo") );
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Conexion.close(rs);
            Conexion.close(cstmt);
            Conexion.close(cn);
        }
        return aula;
    }
}
