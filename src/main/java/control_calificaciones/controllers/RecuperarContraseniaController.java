package control_calificaciones.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RecuperarContraseniaController {
    
    // Componentes
    @FXML 
    private TextField txtCodigoVerificacion;
    @FXML 
    private PasswordField txtNuevaContrasenia;
    @FXML 
    private Button btnReestablecerContrasenia;

    @FXML
    public void reestablecerContrasenia() throws IOException {
        String codigoUsuario = txtCodigoVerificacion.getText().trim();
        String nuevaContraseña = txtNuevaContrasenia.getText().trim();

        // Busqueda del codigo para reestablecer la contraseña del usuario
        
    }
}
