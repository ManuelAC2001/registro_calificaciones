package control_calificaciones.controllers;

import java.io.IOException;
import java.time.LocalDateTime;

import control_calificaciones.App;
import control_calificaciones.data.usuarios.*;
import control_calificaciones.models.usuarios.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EstadisticasController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    // sesion
    private String nombreUsuario;
    private LocalDateTime fechaSesion;
    private LocalDateTime fechaSalida;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    public void cerrarSesion(ActionEvent event) throws IOException {

        fechaSalida = LocalDateTime.now();        

        // GUARADAMOS EN LA BITACORA DE LA BD
        UsuarioDAO.insertarBitacoraSesionUsuario(nombreUsuario, fechaSesion, fechaSalida);

        FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void toPanelPrincipal(ActionEvent event) throws IOException {

        Usuario usuario = new UsuarioDAO().buscar(Sesion.nombreUsuario);

        // Enviar información a la ventana de sesion
        FXMLLoader loader = new FXMLLoader(App.class.getResource("sesion.fxml"));
        root = loader.load();
        SesionController controller = loader.getController();
        controller.iniciarSesion(usuario);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void toBasicas(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(App.class.getResource("estadisticasBasicas.fxml"));
        root = loader.load();
        EstadisticasBasicasController controller = loader.getController();
        controller.iniciarSesion();
        
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        
    }

    @FXML
    public void toCalificaciones(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("estadisticasPromedio.fxml"));
        root = loader.load();
        estadisticasPromedioController controller = loader.getController();
        controller.iniciarSesion();
        
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }


    public void iniciarSesion() {
        nombreUsuario = Sesion.nombreUsuario;
        lblNombreUsuario.setText(Sesion.nombreUsuario);
    }

}
