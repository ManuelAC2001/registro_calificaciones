package control_calificaciones;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        

        String regexCalificacion = "^(\\d{1}\\.)?(\\d+\\.?)+(,\\d{2})?$";
        Double calif = 10.0;
        
        String regexInasistencias = "^[0-9]+$";

        System.out.println("0".matches(regexInasistencias));

    }
}