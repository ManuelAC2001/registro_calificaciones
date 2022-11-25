package control_calificaciones.helpers.emails;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EnviarEmails {
    private static String emailFrom = "tovi.rob20@gmail.com";
    private static String password = "pjjlumumotjbdcxn";

    private String emailTo;
    private String asunto;
    private String contenido;

    private Properties mailPropiedades;
    private Session mailSession;
    private MimeMessage mailCorreo;

    /**
     * @param emailTo   email a quién va dirigido
     * @param asunto    sobre lo que trata el email
     * @param contenido archivo o mensaje que contendra el email
     */

    public EnviarEmails(String emailTo, String asunto, String contenido, File boletaInterna, File boletaExterna) {
        this.emailTo = emailTo;
        this.asunto = asunto;
        this.contenido = contenido;

        mailPropiedades = new Properties();
        crearEmail(boletaInterna, boletaExterna);
        enviarCorreo();
    }

    private void crearEmail(File boletaInterna, File boletaExterna) {
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

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(contenido);

            // archivos de boleta interna a enviar
            MimeBodyPart attachmentPartInterna = new MimeBodyPart();
            attachmentPartInterna.attachFile(boletaInterna);

            // archivos de boletaExterna interna a enviar
            MimeBodyPart attachmentPartExterna = new MimeBodyPart();
            attachmentPartExterna.attachFile(boletaExterna);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPartInterna);
            multipart.addBodyPart(attachmentPartExterna);

            // mailCorreo.setText(contenido, "ISO-8859-1", "html");
            mailCorreo.setContent(multipart);

        } catch (IOException ex) {
            ex.printStackTrace();
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

}
