package control_calificaciones.controllers;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;


import control_calificaciones.App;
import control_calificaciones.data.usuarios.DirectorDAO;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.models.usuarios.Director;
import control_calificaciones.models.usuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class SesionController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    // sesion
    private Usuario usuario;
    private String nombreUsuario;
    private String fechaSesion;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private HBox seccionPersonal;

    // EVENTOS
    @FXML
    public void entrarPersonalSeccion(ActionEvent event) throws IOException {

        usuario = new DirectorDAO().buscar(nombreUsuario);
        if (usuario == null) {
            return;
        }

        Director director = (Director) usuario;

        // IR A LA SIGUIENTE VENTANA DE SECCION PERSONAL

        FXMLLoader loader = new FXMLLoader(App.class.getResource("panelPersonal.fxml"));
        root = loader.load();
        PanelPersonalController controller = loader.getController();
        controller.iniciarSesion(director);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void cerrarSesion(ActionEvent event) throws IOException {

        // mandar a la bitacora
        String fechaSalida = new Date().toString();

        // DATOS A ENVIAR EN LA BITACORA DE SECRETARIAS
        System.out.println(nombreUsuario);
        System.out.println(fechaSesion);
        System.out.println(fechaSalida);

        // GUARADAMOS EN LA BITACORA DE LA BD
        UsuarioDAO.insertarBitacoraSesionUsuario(nombreUsuario, fechaSesion, fechaSalida);
        
        FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void iniciarSesion(Usuario usuario, String fechaSesion) {

        this.fechaSesion = fechaSesion;
        nombreUsuario = usuario.nombreUsuario;
        lblNombreUsuario.setText(nombreUsuario);

        // Esto viola los principios SOLID :(
        if (!usuario.nombreRol.equals("director")) {
            seccionPersonal.setDisable(true);
            return;
        }

    }

}
