package control_calificaciones.controllers;

import java.io.IOException;

import control_calificaciones.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PanelAlumnosController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private void cerrarSesion(ActionEvent event) {
        System.out.println("cerrando sesion");
    }
    
    @FXML
    private void toPanelPrincipal(ActionEvent event) {
        System.out.println("panel principal");
    }

    @FXML
    private void toRegistrarAlumno(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource("registrarAlumno.fxml"));

        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toConsultarAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("consultarAlumno.fxml"));

        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toEliminarAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("eliminarAlumno.fxml"));

        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void toModificarAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarAlumno.fxml"));

        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
