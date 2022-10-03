package control_calificaciones.helpers.emails;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EnviarEmails {
    private static String emailFrom = "tovi.rob20@gmail.com";
    private static String password = "pjjlumumotjbdcxn";

    private String emailTo = "roberttv17@gmail.com";
    private String asunto = "Reestablecer contraseña";
    private String contenido = "120";

    private Properties mailPropiedades;
    private Session mailSession;
    private MimeMessage mailCorreo;

    public EnviarEmails() {
        mailPropiedades = new Properties();
    }

    private void crearEmail() {
        // Simple mail transfer protocol
        mailPropiedades.put("mail.smtp.host", "smtp.gmail.com");
        mailPropiedades.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailPropiedades.setProperty("mail.smtp.starttls.enable", "true");
        mailPropiedades.setProperty("mail.smtp.port", "587");
        mailPropiedades.setProperty("mail.smtp.user", emailFrom);
        mailPropiedades.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mailPropiedades.setProperty("mail.smtp.auth", "true");

        mailSession = Session.getDefaultInstance(mailPropiedades);

        try {
            mailCorreo = new MimeMessage(mailSession);
            mailCorreo.setFrom(new InternetAddress(emailFrom));
            mailCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mailCorreo.setSubject(asunto);
            mailCorreo.setText(contenido, "ISO-8859-1", "html");

        } catch (AddressException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    private void enviarCorreo() {
        try {
            Transport mTransport = mailSession.getTransport("smtp");
            mTransport.connect(emailFrom, password);
            mTransport.sendMessage(mailCorreo, mailCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();

            System.out.println("Correo Enviado");
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } 
    }

    public void enviarEmail() {
        crearEmail();
        enviarCorreo();
    }
}
