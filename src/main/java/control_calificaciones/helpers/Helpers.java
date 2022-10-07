package control_calificaciones.helpers;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Helpers {
    
    public static String generarCodigo() {
        Random r = new Random();
        int cantidad = r.nextInt(900) + 100;
        String codigo = Integer.toString(cantidad);
        return codigo;
    }

    public static String obtenerFechaActual() {
        DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy/MMMM/dd");
        String fechaNow = fecha.format(LocalDateTime.now());
        String fechaMod = fechaNow.replaceAll("/", "-");
        System.out.println(fechaMod);
        return fechaMod;
    }

    public static File crearCarpeta (String ruta) {
        File RUTA_CARPETA_PDF = new File(ruta);
        if (!RUTA_CARPETA_PDF.exists()) {
            if (RUTA_CARPETA_PDF.mkdirs()) {
                System.out.println("Se creo carpeta");
            } else {
                System.out.println("Error al crear carpeta");
            }
        }
        return RUTA_CARPETA_PDF;
    }
}
