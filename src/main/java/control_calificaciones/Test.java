package control_calificaciones;
// import control_calificaciones.helpers.emails.EnviarEmails;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import control_calificaciones.data.usuarios.DirectorDAO;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.models.usuarios.Usuario;

import control_calificaciones.helpers.Helpers;
import control_calificaciones.helpers.emails.EnviarEmails;
import control_calificaciones.helpers.pdf.GenerarPDF;

public class Test {

    public static void main(String[] args) {
        // EnviarEmails enviarEmail = new EnviarEmails();
        // enviarEmail.enviarEmail();

        // System.out.println( Helpers.generarCodigo() );

        // GENERAR PDF
        // GenerarPDF.generarPDF();

        // System.out.println("AGREGANDO SECRETARIA");

        // DirectorDAO dao = new DirectorDAO();

        // //creamos la secretaria
        // Usuario secretaria = new Usuario();
        // secretaria.nombreUsuario = "secretaria3";
        // secretaria.correo = "secretaria2@gmail.com";
        // secretaria.contrasenia = "1234";

        // UsuarioDAO buscadorUsuario = new UsuarioDAO();

        // if(buscadorUsuario.nombreUsuarioExiste(secretaria.nombreUsuario)){
        // System.out.println("El nombre de usuario ya existe");
        // return;
        // }

        // if(buscadorUsuario.correoExiste(secretaria.correo)){
        // System.out.println("El correo ya esta en uso");
        // return;
        // }

        // dao.insertarSecretaria(secretaria);
        // System.out.println(new UsuarioDAO().buscar(secretaria.nombreUsuario));

        System.out.println(LocalDateTime.now().getMonth());
        System.out.println(LocalDateTime.now().getYear());
        System.out.println(LocalDateTime.now().getDayOfMonth());
        System.out.println(LocalDateTime.now().toLocalTime());


    }

}
