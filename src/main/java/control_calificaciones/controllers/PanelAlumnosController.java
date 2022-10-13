package control_calificaciones.controllers;

import java.io.IOException;
import java.time.LocalDateTime;

import control_calificaciones.App;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.models.usuarios.Sesion;
import control_calificaciones.models.usuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PanelAlumnosController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnConsultar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnRegistrar;

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        // GUARADAMOS EN LA BITACORA DE LA BD
        UsuarioDAO.insertarBitacoraSesionUsuario(Sesion.nombreUsuario, Sesion.fechaSesion, LocalDateTime.now());

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
    private void toRegistrarAlumno(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource("registrarAlumno.fxml"));
        root = loader.load();
        RegistrarAlumnoController controller = loader.getController();
        controller.iniciarSesion();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toConsultarAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("consultarAlumno.fxml"));
        root = loader.load();

        ConsultarAlumnoController controller = loader.getController();
        controller.iniciarSesion();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toEliminarAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("eliminarAlumno.fxml"));
        root = loader.load();
        EliminarAlumnoController controller = loader.getController();
        controller.iniciarSesion();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toModificarAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarAlumno.fxml"));
        root = loader.load();
        ModificarAlumnoController controller = loader.getController();
        controller.iniciarSesion();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void iniciarSesion() {
        lblNombreUsuario.setText(Sesion.nombreUsuario);

        Usuario usuario = new UsuarioDAO().buscar(Sesion.nombreUsuario);

        // Esto viola los principios SOLID :(
        if (!usuario.nombreRol.equals("director")) {
            btnEliminar.setDisable(true);
            btnActualizar.setDisable(true);
            return;
        }

    }

}
