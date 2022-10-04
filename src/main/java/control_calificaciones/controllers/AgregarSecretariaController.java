package control_calificaciones.controllers;

import java.io.IOException;

import control_calificaciones.data.usuarios.DirectorDAO;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.models.usuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AgregarSecretariaController {

    @FXML
    private Label lblNombreUsuario;
    
    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtCorreo;


    private Usuario usuario;
    private String nombreUsuario;


    public void iniciarSesion(Usuario usuario){
        nombreUsuario = usuario.nombreUsuario;
        lblNombreUsuario.setText(nombreUsuario);
    }

    @FXML
    public void registrarSecretaria(ActionEvent event) throws IOException {
        
        //Capturamos los datos
        Usuario secretaria = new Usuario();
        secretaria.nombreUsuario = txtNombreUsuario.getText().trim();
        secretaria.correo = txtCorreo.getText().trim();
        secretaria.contrasenia = txtContrasenia.getText().trim();

        DirectorDAO dao = new DirectorDAO();

        UsuarioDAO buscadorUsuario =  new UsuarioDAO();

        if(buscadorUsuario.nombreUsuarioExiste(secretaria.nombreUsuario)){
            System.out.println("El nombre de usuario ya existe");
            return;
        }

        if(buscadorUsuario.correoExiste(secretaria.correo)){
            System.out.println("El correo ya esta en uso");
            return;
        }
        
        dao.insertarSecretaria(secretaria);
        System.out.println("se agrego :)");

    }
    
}
