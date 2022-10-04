package control_calificaciones.controllers;

import java.io.IOException;

import control_calificaciones.App;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.models.usuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

    private Parent root;
    private Scene scene;
    private Stage stage;

    // Componentes
    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private Label lblContrasenia;

    @FXML
    public void ingresar(ActionEvent event) throws IOException {

        String nombreUsuario = txtNombreUsuario.getText().trim();
        String contrasenia = txtContrasenia.getText().trim();

        // preparamos la busqueda
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscar(nombreUsuario);

        if (nombreUsuario.isEmpty() || contrasenia.isEmpty()) {
        setInputError(lblNombreUsuario, txtNombreUsuario, "El campo nombre de usuario es requerido");
        setInputError(lblContrasenia, txtContrasenia, "El campo contraseña es requerido");
        return;
        }

        if (usuario == null) {
        setInputError(lblNombreUsuario, txtNombreUsuario, "Nombre de usuario incorrecto o no existe");
        return;
        }

        //quitamos los mensajes de error
        lblNombreUsuario.setVisible(false);
        txtNombreUsuario.getStyleClass().remove("input-error");

        if(!usuario.contrasenia.equals(contrasenia)){
        setInputError(lblContrasenia, txtContrasenia, "Contraseña incorrecta");
        return;
        }

        // Enviar información a la ventana de sesion
        FXMLLoader loader = new FXMLLoader(App.class.getResource("sesion.fxml"));
        root = loader.load();
        SesionController controller = loader.getController();
        controller.iniciarSesion(usuario);
        
        stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setInputError(Label lbl, TextField txt, String message) {
        txt.getStyleClass().add("input-error");
        lbl.setVisible(true);
        lbl.setText(message);
    }

    @FXML
    public void btnRecuperar() throws IOException {
        App.setRoot("recuperar");
    }

}
