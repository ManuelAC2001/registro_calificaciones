package control_calificaciones;

import java.util.Scanner;

import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.models.usuarios.Usuario;

public class Test {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.print("Ingrese el nombre de ususario: ");
        String username = scan.nextLine();
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario user = usuarioDAO.buscar(username);
        
        if(user == null){
            System.out.println("El usuario no existe :c");
            return;
        }

        System.out.print("Ingrese password: ");
        String password = scan.nextLine();

        if(!password.equals(user.contrasenia)){
            System.out.println("contraseña incorrecta");
            return;
        }

        System.out.println("bienvenido c:");

        
    }

}
