package control_calificaciones.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

public class Conexion {

    static Dotenv env = Dotenv.configure().load();

    public static final String HOST     = env.get("DB_HOST");
    public static final String PORT     = env.get("DB_PORT");
    public static final String DB_NAME  = env.get("DB_NAME");
    public static final String USER     = env.get("DB_USERNAME");
    public static final String PASSWORD = env.get("DB_PASSWORD");

    // public static final String HOST     = "localhost";
    // public static final String PORT     = "3306";
    // public static final String DB_NAME  = "registro_calificaciones";
    // public static final String USER     = "root";
    // public static final String PASSWORD = "";

    public static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME
            + "?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Metodos sobrecargados
    public static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(Statement smtm) {
        try {
            smtm.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(PreparedStatement smtm) {
        try {
            smtm.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(Connection cn) {
        try {
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
