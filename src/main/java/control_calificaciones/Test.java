package control_calificaciones;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        

        String regexCalificacion = "^(\\d{1}\\.)?(\\d+\\.?)+(,\\d{2})?$";
        Double calif = 10.0;
        

        System.out.println(String.format("%.2f", calif));

    }
}