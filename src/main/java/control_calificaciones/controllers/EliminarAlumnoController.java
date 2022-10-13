package control_calificaciones.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import control_calificaciones.App;
import control_calificaciones.data.AlumnoDAO;
import control_calificaciones.data.TutorDAO;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.models.Alumno;
import control_calificaciones.models.Aula;
import control_calificaciones.models.CorreoTutor;
import control_calificaciones.models.Tutor;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EliminarAlumnoController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    private Alert alert;
    private Alumno alumno;

    @FXML
    private DatePicker DateAlumnoFecha;

    @FXML
    private Button btnEliminarAlumno;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private ComboBox<String> optionSexoAlumno;

    @FXML
    private TextField txtApellidoMatAlumno;

    @FXML
    private TextField txtApellidoPaTutor;

    @FXML
    private TextField txtApellidoPatAlumno;

    @FXML
    private TextField txtCorreoTutor;

    @FXML
    private TextField txtCorreoTutor2;

    @FXML
    private TextField txtCurpAlumno;

    @FXML
    private TextField txtFechaNacimientoAlumno;

    @FXML
    private TextField txtGradoAlumno;

    @FXML
    private TextField txtGrupoAlumno;

    @FXML
    private TextField txtMaTutor;

    @FXML
    private TextField txtNombreAlumno;

    @FXML
    private TextField txtNombreTutor;

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
    private void buscar(ActionEvent event) throws IOException {

        // caputrando el nombre completo del alumno desde la UI
        String alumnoNombre = txtNombreAlumno.getText().trim();
        String alumnoApellidoP = txtApellidoPatAlumno.getText().trim();
        String alumnoApellidoM = txtApellidoMatAlumno.getText().trim();

        // validacion de la UI
        // ...

        AlumnoDAO alumnoDAO = new AlumnoDAO();
        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutor = new Tutor();
        ArrayList<CorreoTutor> correosTutor = new ArrayList<>();

        alumno = new Alumno();
        alumno.setNombre(alumnoNombre);
        alumno.setApellido_paterno(alumnoApellidoP);
        alumno.setApellido_materno(alumnoApellidoM);

        alumno = alumnoDAO.buscarByNombreCompleto(alumno);

        if (alumno == null) {

            txtCurpAlumno.setText("");
            optionSexoAlumno.setValue("");
            DateAlumnoFecha.setValue(null);
            txtGradoAlumno.setText("");
            txtGrupoAlumno.setText("");

            txtNombreTutor.setText("");
            txtApellidoPaTutor.setText("");
            txtMaTutor.setText("");

            txtCorreoTutor.setText("");
            txtCorreoTutor2.setText("");

            btnEliminarAlumno.setDisable(true);

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El alumno a buscar no existe");
            alert.showAndWait();
            return;
        }

        tutor = tutorDAO.buscarById(alumno.getId_tutor());
        correosTutor = tutorDAO.getCorreos(tutor);

        // estableciendo los datos del alumno en la UI
        Aula aula = alumnoDAO.getAula(alumno);

        txtCurpAlumno.setText(alumno.getCurp());
        optionSexoAlumno.setValue(alumno.getGenero().toString());
        DateAlumnoFecha.setValue(alumno.getFecha_nacimiento().toLocalDate());
        txtGradoAlumno.setText(aula.getNombre_grado());
        txtGrupoAlumno.setText(aula.getNombre_grupo());

        // estableciendo los datos del tutor desde la Ui
        txtNombreTutor.setText(tutor.getNombre());
        txtApellidoPaTutor.setText(tutor.getApellido_paterno());
        txtMaTutor.setText(tutor.getApellido_materno());

        // estableciendo correos de los tutores
        txtCorreoTutor.setText(correosTutor.get(0).getCorreo());

        if (correosTutor.size() > 1) {
            txtCorreoTutor2.setText(correosTutor.get(1).getCorreo());
        }

        btnEliminarAlumno.setDisable(false);
    }

    @FXML
    private void eliminarAlumno(ActionEvent event) throws IOException {

        if (alumno == null) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El alumno a eliminar no existe");
            alert.showAndWait();
            return;
        }
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        alumnoDAO.eliminar(alumno);

        // vamos a la siguiente ventana
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Mensaje");
        alert.setContentText("alumno eliminado correctamente");
        alert.showAndWait();
    }

}
