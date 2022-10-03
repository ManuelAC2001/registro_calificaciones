package control_calificaciones;

import control_calificaciones.helpers.emails.EnviarEmails;

public class Test {

    public static void main(String[] args) {
        EnviarEmails enviarEmail = new EnviarEmails();
        enviarEmail.enviarEmail();
    }

}
