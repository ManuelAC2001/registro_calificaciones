package control_calificaciones.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import control_calificaciones.models.Aula;

public class AulaDAO {

    public ArrayList<Aula> getAulas(){

        ArrayList<Aula> aulas = new ArrayList<>();
        String callProcedure = "{CALL getAulas()}";

        Connection cn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(callProcedure);
            rs = cstmt.executeQuery();

            while(rs.next()){
                Aula aula = new Aula();

                aula.setId_aula( rs.getInt("id_aula") );
                aula.setId_grado( rs.getInt("id_grado") );
                aula.setNombre_grado( rs.getString("nombre_grado") );
                aula.setId_grupo( rs.getInt("id_grupo") );
                aula.setNombre_grupo( rs.getString("nombre_grupo") );
                aula.setCantidad( rs.getInt("cantidad") );

                aulas.add(aula);
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
        return aulas;
    }
    
    public ArrayList<Aula> getAulasByNombreGrado(String nombre_grado){

        ArrayList<Aula> aulas = new ArrayList<>();

        String callProcedure = "{ CALL getAulasByNombreGrado(?) }";

        Connection cn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(callProcedure);
            cstmt.setString(1, nombre_grado);

            rs = cstmt.executeQuery();

            while(rs.next()){
                Aula aula = new Aula();

                aula.setId_aula( rs.getInt("id_aula") );
                aula.setId_grado( rs.getInt("id_grado") );
                aula.setNombre_grado( rs.getString("nombre_grado") );
                aula.setId_grupo( rs.getInt("id_grupo") );
                aula.setNombre_grupo( rs.getString("nombre_grupo") );
                aula.setCantidad( rs.getInt("cantidad") );

                aulas.add(aula);
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

        return aulas;
    }

    public void insertar(Aula aula){

        String procedureCall = "{CALL insertarAula( ?, ? )}";
        Connection cn = null;
        CallableStatement csmt = null;

        try {
            cn = Conexion.getConnection();
            csmt = cn.prepareCall(procedureCall);

            csmt.setInt(1, aula.getId_grado());
            csmt.setInt(2, aula.getId_grupo());

            csmt.executeUpdate();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Conexion.close(csmt);
            Conexion.close(cn);
        }
    }

    public Aula buscarAula(Aula aula){

        Aula aulaEncontrada = new Aula();

        String callProcedure = "{CALL getAulaByGrupoGrado(?, ?)}";

        Connection cn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(callProcedure);

            cstmt.setInt(1, aula.getId_grado());
            cstmt.setInt(2, aula.getId_grupo());

            rs = cstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            aulaEncontrada.setId_aula( rs.getInt(1) );
            aulaEncontrada.setId_grado( rs.getInt(2) );
            aulaEncontrada.setId_grupo( rs.getInt(3) );
            
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            Conexion.close(rs);
            Conexion.close(cstmt);
            Conexion.close(cn);
        }

        return aulaEncontrada;
        
    }    
}
