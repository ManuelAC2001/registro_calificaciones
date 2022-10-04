package control_calificaciones.controllers;


import java.io.IOException;

import control_calificaciones.App;
import control_calificaciones.data.usuarios.DirectorDAO;
import control_calificaciones.models.usuarios.Director;
import control_calificaciones.models.usuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PanelPersonalController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    private Usuario usuario;
    private String nombreUsuario;

    @FXML
    private Label lblNombreUsuario;

    public void iniciarSesion(Usuario usuario){
        nombreUsuario = usuario.nombreUsuario;
        lblNombreUsuario.setText(nombreUsuario);
    }

    @FXML
    public void crearUsuario(ActionEvent event) throws IOException {

        usuario = new DirectorDAO().buscar(nombreUsuario);
        if (usuario == null) {
            return;
        }

        Director director = (Director) usuario;
        
        //vamos a la ventana de de agregar personal
        
        FXMLLoader loader = new FXMLLoader(App.class.getResource("agregarSecretaria.fxml"));
        root = loader.load();
        AgregarSecretariaController controller = loader.getController();
        controller.iniciarSesion(director);

        stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    
}
