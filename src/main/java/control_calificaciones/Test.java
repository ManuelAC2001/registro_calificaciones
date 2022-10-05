package control_calificaciones;

import control_calificaciones.helpers.Helpers;
import control_calificaciones.helpers.emails.EnviarEmails;
import control_calificaciones.helpers.pdf.GenerarPDF;

public class Test {

    public static void main(String[] args) {
      /*   EnviarEmails enviarEmail = new EnviarEmails();
        enviarEmail.enviarEmail(); */

        //System.out.println( Helpers.generarCodigo() );

        GenerarPDF.generarPDF();
    }

}
