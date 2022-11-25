package control_calificaciones.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import control_calificaciones.App;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.data.usuarios.UsuarioDAOH;
import control_calificaciones.models.usuarios.Sesion;
import control_calificaciones.models.usuarios.Usuario;
import control_calificaciones.models.usuarios.UsuarioH;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ModificarSecretariaController {

    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    private Button btnModificar;

    @FXML
    private Label lblContrasenia;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private Label msgNombreUsuario;

    @FXML
    private Label msgNombreUsuario1;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombreUsuario;

    Optional<UsuarioH> optional;
    UsuarioDAOH usuarioDAO = new UsuarioDAOH();

    String regexUserName = "^[A-Za-z][A-Za-z0-9_]{4,29}$";
    String regexGmail = "[^@ \\t\\r\\n]+@gmail\\.com";

    public void iniciarSesion(Usuario usuario) {
        String nombreUsuario = usuario.nombreUsuario;
        lblNombreUsuario.setText(nombreUsuario);
    }

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
    private void buscarSecretaria(ActionEvent event) {

        String nombreUsuario = txtNombreUsuario.getText().trim();

        optional = buscarUsuarioByNombre(nombreUsuario);

        if (!optional.isPresent()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("Secretaria no encontrada");
            alert.showAndWait();

            txtNombreUsuario.clear();
            txtCorreo.clear();
            btnModificar.setDisable(true);
            return;
        }

        UsuarioH secretaria = optional.get();

        txtNombreUsuario.setText(secretaria.getNombreUsuario());
        txtCorreo.setText(secretaria.getCorreo());
        btnModificar.setDisable(false);

    }

    @FXML
    private void modificarSecretaria(ActionEvent event) throws IOException {

        UsuarioH secretaria = optional.get();

        if (secretaria.getRol().getNombre().equals("director")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("No se puede modificar la infomación del director");
            alert.showAndWait();
            return;
        }

        if (!optional.isPresent()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("Secretaria no encontrado");
            alert.showAndWait();
            return;
        }

        String nombreUsuario = txtNombreUsuario.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (!nombreUsuario.matches(regexUserName)) {
            txtNombreUsuario.getStyleClass().add("input-error");
            msgNombreUsuario.setVisible(true);
            msgNombreUsuario.setText("Formato de nombre de usuario no valido");
            return;
        }
        txtNombreUsuario.getStyleClass().remove("input-error");
        msgNombreUsuario.setVisible(false);

        if (!correo.matches(regexGmail)) {
            txtCorreo.getStyleClass().add("input-error");
            lblCorreo.setVisible(true);
            lblCorreo.setText("Formato de correo no valido");
            return;
        }
        txtCorreo.getStyleClass().remove("input-error");
        lblCorreo.setVisible(false);

        // VALIDACION DESDE EL SERVIDOR

        Optional<UsuarioH> optionalCorreo = buscarUsuarioByCorreo(correo);
        Optional<UsuarioH> optionalNombreUsuario = buscarUsuarioByNombre(nombreUsuario);

        if (optionalNombreUsuario.isPresent()) {

            if (!optionalNombreUsuario.get().equals(secretaria)) {
                txtNombreUsuario.getStyleClass().add("input-error");
                msgNombreUsuario.setVisible(true);
                msgNombreUsuario.setText("El nombre de usuario ya esta en uso");
                return;
            }

        }

        if (optionalCorreo.isPresent()) {

            if (!optionalCorreo.get().equals(secretaria)) {
                txtCorreo.getStyleClass().add("input-error");
                lblCorreo.setVisible(true);
                lblCorreo.setText("El correo ya esta en uso");
                return;
            }

        }

        // confirmacion de modificacion
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de la modificación de secretaria");
        alert.setContentText("¿Esta seguro que desea modificar a esta secretaria?");
        Optional<ButtonType> btnRespuesta = alert.showAndWait();

        if (btnRespuesta.get() != ButtonType.OK) {
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Operación cancelada");
            alert.setContentText("La secretaria no fue modificada");
            alert.showAndWait();
            return;
        }

        secretaria.setNombreUsuario(nombreUsuario);
        secretaria.setCorreo(correo);

        usuarioDAO.actualizar(secretaria);

        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setContentText("Secretaria modificada correctamente");
        alert.showAndWait();
        txtNombreUsuario.clear();
        txtCorreo.clear();

    }

    private Optional<UsuarioH> buscarUsuarioByNombre(String nombreUsuario) {

        Optional<UsuarioH> optional = usuarioDAO.listar()
                .stream()
                .filter(u -> u.getNombreUsuario().equals(nombreUsuario))
                .findFirst();

        return optional;
    }

    private Optional<UsuarioH> buscarUsuarioByCorreo(String correo) {

        Optional<UsuarioH> optional = usuarioDAO.listar()
                .stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
                .findFirst();

        return optional;
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

}
