package control_calificaciones.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.github.cdimascio.dotenv.Dotenv;

public class Respaldo {

    private static Dotenv env = Dotenv.configure().load();

    private static final String HOST     = env.get("DB_HOST");
    private static final String DB_NAME  = env.get("DB_NAME");
    private static final String USERNAME = env.get("DB_USERNAME");
    private static final String PASSWORD = env.get("DB_PASSWORD");
    private static final String PORT     = env.get("DB_PORT");

    //Linea de codigo que genera el errror en el JAR
    private static final String SQL_FILE = "respaldo\\respaldo.sql";

    public static void respaldar() {
        try {
            /* El nombre o ruta a donde se guardara el archivo de volcado .sql */
            String command = "bin\\mysqldump" +
                    " -u " + USERNAME +
                    " --password=" + PASSWORD + " -h " + HOST +
                    " --port=" + PORT + " " + DB_NAME +
                    " -r " + SQL_FILE;

            /* Se crea un proceso que ejecuta el comando dado */
            Process bck = Runtime.getRuntime().exec(command);

            /*
             * Obtiene el flujo de datos del proceso, esto se hace para obtener
             * el texto del proceso
             */
            InputStream stdout = bck.getErrorStream();

            /* Se obtiene el resultado de finalizacion del proceso */
            int resultado = bck.waitFor();

            String line;

            /*
             * Se crea un buffer de lectura con el flujo de datos outstd y ese mismo
             * sera leido e impreso, esto mostrara el texto que muestre el programa
             * mysqldump, de esta forma sabra cual es el error en su comando
             */
            BufferedReader brCleanUp = new BufferedReader(new InputStreamReader(stdout));
            while ((line = brCleanUp.readLine()) != null) {
                System.out.println(line);
            }
            brCleanUp.close();

            if (resultado == 0) {
                System.out.println("Respaldo exitoso");
            } else {
                System.out.println("Error al respaldar");
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

    }

    public static void restaurar() {
        try {

            String command[] = new String[] { "bin\\mysql", DB_NAME, "-h" + HOST, "--port=" + PORT, "-u" + USERNAME,
                    "--password=" + PASSWORD,
                    "-e", " source " + SQL_FILE };

            /* Se crea un proceso que ejecuta el comando dado */
            Process bck = Runtime.getRuntime().exec(command);

            /*
             * Obtiene el flujo de datos del proceso, esto se hace para obtener
             * el texto del proceso
             */
            InputStream stdout = bck.getErrorStream();

            /* Se obtiene el resultado de finalizacion del proceso */
            int resultado = bck.waitFor();

            String line;

            /*
             * Se crea un buffer de lectura con el flujo de datos outstd y ese mismo
             * sera leido e impreso, esto mostrara el texto que muestre el programa
             * mysqldump, de esta forma sabra cual es el error en su comando
             */
            BufferedReader brCleanUp = new BufferedReader(new InputStreamReader(stdout));
            while ((line = brCleanUp.readLine()) != null) {
                System.out.println(line);
            }
            brCleanUp.close();

            if (resultado == 0) {
                System.out.println("Restauración exitosa");
            } else {
                System.out.println("Error al restaurar");
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

}
