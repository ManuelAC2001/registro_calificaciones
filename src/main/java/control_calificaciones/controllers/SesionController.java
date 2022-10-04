package control_calificaciones.controllers;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import control_calificaciones.data.usuarios.DirectorDAO;
import control_calificaciones.models.usuarios.Director;
import control_calificaciones.models.usuarios.Usuario;
import javafx.fxml.FXML;

public class SesionController {

    private Usuario usuario;
    private String nombreUsuario;

    @FXML
    private Label lblNombreUsuario;
    
    @FXML
    private HBox seccionPersonal;
    
    //EVENTOS
    @FXML
    public void entrarPersonalSeccion(){

        usuario = new DirectorDAO().buscar(nombreUsuario);
        if(usuario == null){
            return;
        }
        
        Director director = (Director) usuario;
        
        //IR A LA SIGUIENTE VENTANA DE SECCION PERSONAL
        
    }
    
    public void iniciarSesion(Usuario usuario){

        nombreUsuario = usuario.nombreUsuario;
        lblNombreUsuario.setText(nombreUsuario);

        //Esto viola los principios SOLID :(
        if(!usuario.nombreRol.equals("director")){
            seccionPersonal.setDisable(true);
            return;
        }
         
    }
    
}
