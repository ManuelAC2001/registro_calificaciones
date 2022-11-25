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

public class ConsultarSecretariaController {

    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    private Button btnConsultar;

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
    private PasswordField txtContrasenia;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombreUsuario;


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
    private void consultarSecretaria(ActionEvent event) throws IOException {

        String valueNombreUsuario = txtNombreUsuario.getText().trim();

        Optional<UsuarioH> optional = new UsuarioDAOH().listar()
                .stream()
                .filter(u -> u.getNombreUsuario().equals(valueNombreUsuario))
                .findFirst();

        if(!optional.isPresent()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("Secretaria no encontrado");
            alert.showAndWait();


            txtNombreUsuario.clear();
            txtCorreo.clear();        
            return;
        }

        UsuarioH secretaria = optional.get();

        txtNombreUsuario.setText(secretaria.getNombreUsuario());
        txtCorreo.setText( secretaria.getCorreo());        


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
