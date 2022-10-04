package control_calificaciones.helpers;

import java.util.Random;

public class Helpers {
    
    public static String generarCodigo() {
        Random r = new Random();
        int cantidad = r.nextInt(900) + 100;
        String codigo = Integer.toString(cantidad);
        return codigo;
    }
}
