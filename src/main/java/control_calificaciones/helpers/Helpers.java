package control_calificaciones.helpers;

// import java.time.LocalDate;
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
}
