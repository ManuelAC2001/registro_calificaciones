package control_calificaciones;

import control_calificaciones.database.Respaldo;

public class Test {

    public static void main(String[] args) {
        
        Respaldo.respaldar();
        Respaldo.restaurar();

    }
    
}
