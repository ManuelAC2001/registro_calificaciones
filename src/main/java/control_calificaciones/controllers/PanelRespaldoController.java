package control_calificaciones.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import control_calificaciones.App;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.database.Respaldo;
import control_calificaciones.models.usuarios.Sesion;
import control_calificaciones.models.usuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PanelRespaldoController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    private Alert alert;

    // sesion
    private String nombreUsuario;
    private LocalDateTime fechaSesion;
    private LocalDateTime fechaSalida;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnRegistrar;

    @FXML
    private ImageView iconActualizar;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private void respaldar(ActionEvent event) {

        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación del respaldo");
        alert.setContentText("¿Esta seguro que desea respaldar la información del sistema?");
        Optional<ButtonType> btnRespuesta = alert.showAndWait();

        if (btnRespuesta.get() != ButtonType.OK) {
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Operación cancelada");
            alert.setContentText("El respaldo no se concreto");
            alert.showAndWait();
            return;
        }

        Respaldo.respaldar();

        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Respaldo exitoso");
        alert.setContentText("El respaldo se realizó correctamente");
        alert.showAndWait();

    }

    @FXML
    private void restaurar(ActionEvent event) {

        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de la restauración");
        alert.setContentText("¿Esta seguro que desea restaurar la información del sistema?");
        Optional<ButtonType> btnRespuesta = alert.showAndWait();

        if (btnRespuesta.get() != ButtonType.OK) {
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Operación cancelada");
            alert.setContentText("La restauración no se concreto");
            alert.showAndWait();
            return;
        }

        Respaldo.restaurar();

        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Restauración exitosa");
        alert.setContentText("La restauración se realizó correctamente");
        alert.showAndWait();

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

    public void iniciarSesion() {
        nombreUsuario = Sesion.nombreUsuario;
        lblNombreUsuario.setText(Sesion.nombreUsuario);
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
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

}
