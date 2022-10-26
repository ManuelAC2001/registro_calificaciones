package control_calificaciones.controllers;

import java.io.IOException;
import java.time.LocalDateTime;

import control_calificaciones.App;
import control_calificaciones.data.usuarios.DirectorDAO;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.models.usuarios.Director;
import control_calificaciones.models.usuarios.Sesion;
import control_calificaciones.models.usuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SesionController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    // sesion
    private Usuario usuario;
    private String nombreUsuario;
    private LocalDateTime fechaSesion;
    private LocalDateTime fechaSalida;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private HBox seccionPersonal;

    @FXML
    private HBox seccionEstadisticas;

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
    public void seccionBitacora(ActionEvent event) throws IOException{
        
        usuario = new DirectorDAO().buscar(nombreUsuario);
        if (usuario == null) {
            return;
        }
        
        // IR A LA SIGUIENTE VENTANA DE SECCION PERSONAL
        FXMLLoader loader = new FXMLLoader(App.class.getResource("bitacoraUsuarios.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void entrarAlumnosSeccion(ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader(App.class.getResource("panelAlumnos.fxml"));
        root = loader.load();
        PanelAlumnosController controller = loader.getController();
        controller.iniciarSesion();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void seccionListaAlumnos(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource("listaAlumnos.fxml"));
        root = loader.load();
        ListaAlumnosController controller = loader.getController();
        controller.iniciarSesion();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void entrarCapturaCalificaciones(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource("capturaCalificaciones.fxml"));
        root = loader.load();
        CapturaCalificacionesController controller = loader.getController();
        controller.iniciarSesion();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void iniciarSesion(Usuario usuario) {
        
        nombreUsuario = usuario.nombreUsuario;

        this.fechaSesion = LocalDateTime.now();
        Sesion.fechaSesion = this.fechaSesion; //aaaa
        Sesion.nombreUsuario = nombreUsuario;

        lblNombreUsuario.setText(nombreUsuario);

        // Esto viola los principios SOLID :(
        if (!usuario.nombreRol.equals("director")) {
            seccionPersonal.setDisable(true);
            seccionEstadisticas.setDisable(true);
            return;
        }

    }

}
