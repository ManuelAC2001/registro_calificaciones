package control_calificaciones.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import control_calificaciones.App;
import control_calificaciones.data.AulaDAOH;
import control_calificaciones.data.usuarios.*;
import control_calificaciones.models.AulaH;
import control_calificaciones.models.usuarios.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class estadisticasPromedioController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    // sesion
    private Usuario usuario;
    private String nombreUsuario;
    private LocalDateTime fechaSesion;
    private LocalDateTime fechaSalida;

    private List<AulaH> aulas = new AulaDAOH()
            .listar()
            .stream()
            .filter(aula -> !aula.getAlumnos().isEmpty())
            .collect(Collectors.toList());

    @FXML
    private Label lblNombreUsuario;

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {

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
    void seccionBitacora(ActionEvent event) throws IOException {

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
    void seccionListaAlumnos(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("listaAlumnos.fxml"));
        root = loader.load();
        ListaAlumnosController controller = loader.getController();
        controller.iniciarSesion();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void iniciarSesion(Usuario usuario) {

        nombreUsuario = usuario.nombreUsuario;

        this.fechaSesion = LocalDateTime.now();
        Sesion.fechaSesion = this.fechaSesion; // aaaa
        Sesion.nombreUsuario = nombreUsuario;

        lblNombreUsuario.setText(nombreUsuario);

        // Esto viola los principios SOLID :(
        if (!usuario.nombreRol.equals("director")) {
            return;
        }

    }

    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        aulas.forEach(a -> System.out.println(a));

    }

}
