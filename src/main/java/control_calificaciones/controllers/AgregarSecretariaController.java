package control_calificaciones.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import control_calificaciones.App;
import control_calificaciones.data.usuarios.DirectorDAO;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.models.usuarios.Sesion;
import control_calificaciones.models.usuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AgregarSecretariaController {

    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    private Label lblContrasenia;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private Label msgNombreUsuario;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private TextField txtCorreo;

    private Usuario usuario;
    private String nombreUsuario;

    public void iniciarSesion(Usuario usuario) {
        nombreUsuario = usuario.nombreUsuario;
        lblNombreUsuario.setText(nombreUsuario);
    }

    @FXML
    public void registrarSecretaria(ActionEvent event) throws IOException {

        // se que esta mal ahi we, pero equis
        DirectorDAO dao = new DirectorDAO();
        UsuarioDAO buscadorUsuario = new UsuarioDAO();
        usuario = new UsuarioDAO().buscar(nombreUsuario);

        // validacion desde frontend
        String nombreUsuarioValue = txtNombreUsuario.getText().trim();
        String contraseniaValue = txtContrasenia.getText().trim();
        String correoValue = txtCorreo.getText().trim();

        ArrayList<String> camposValores = new ArrayList<>();
        camposValores.add(nombreUsuarioValue);
        camposValores.add(contraseniaValue);
        camposValores.add(correoValue);

        if (camposValores.contains("")) {
            msgNombreUsuario.setVisible(true);
            lblCorreo.setVisible(true);
            lblContrasenia.setVisible(true);
            
            txtNombreUsuario.getStyleClass().add("input-error");
            txtCorreo.getStyleClass().add("input-error");
            txtContrasenia.getStyleClass().add("input-error");
            return;
        }
        
        if(txtCorreo.getText().contains("1") || txtCorreo.getText().contains("2")
        || txtCorreo.getText().contains("3") || txtCorreo.getText().contains("4")){
            txtCorreo.getStyleClass().add("input-error");
            lblCorreo.setVisible(true);
            lblCorreo.setText("No se permite ingresar numeros como correo");
            return;
        }

        if(txtNombreUsuario.getText().contains("1") || txtNombreUsuario.getText().contains("2")
        || txtNombreUsuario.getText().contains("3") || txtNombreUsuario.getText().contains("4")){
            txtNombreUsuario.getStyleClass().add("input-error");
            msgNombreUsuario.setVisible(true);
            msgNombreUsuario.setText("No se permite ingresar numeros como nombre de usuario");
            return;
        }

        // Capturamos los datos
        Usuario secretaria = new Usuario();
        secretaria.nombreUsuario = txtNombreUsuario.getText().trim();
        secretaria.correo = txtCorreo.getText().trim();
        secretaria.contrasenia = txtContrasenia.getText().trim();

        // validacion en el servidor
        if (buscadorUsuario.nombreUsuarioExiste(secretaria.nombreUsuario)) {
            txtNombreUsuario.getStyleClass().add("input-error");
            msgNombreUsuario.setVisible(true);
            msgNombreUsuario.setText("El nombre de usuario ya esta en uso");
            return;
        }
        msgNombreUsuario.setVisible(false);

        if (buscadorUsuario.correoExiste(secretaria.correo)) {
            txtCorreo.getStyleClass().add("input-error");
            lblCorreo.setVisible(true);
            lblCorreo.setText("El correo ya esta en uso");
            return;
        }
        lblCorreo.setVisible(false);

        dao.insertarSecretaria(secretaria);

        // Mandar mensaje al usuario
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setContentText("El usuario ha sido agregado correctamente");
        alert.showAndWait();

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
    public void toPanelPrincipal(ActionEvent event) throws IOException{
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
    public void cerrarSesion(ActionEvent event) throws IOException {

        // GUARADAMOS EN LA BITACORA DE LA BD
        UsuarioDAO.insertarBitacoraSesionUsuario(Sesion.nombreUsuario, Sesion.fechaSesion, LocalDateTime.now());

        FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
