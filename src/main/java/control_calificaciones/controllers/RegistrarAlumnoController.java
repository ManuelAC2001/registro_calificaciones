package control_calificaciones.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control_calificaciones.App;
import control_calificaciones.data.AlumnoDAO;
import control_calificaciones.data.AulaDAO;
import control_calificaciones.data.CorreoTutorDAO;
import control_calificaciones.data.TutorDAO;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.helpers.CurpValidador;
import control_calificaciones.models.Alumno;
import control_calificaciones.models.Aula;
import control_calificaciones.models.Tutor;
import control_calificaciones.models.usuarios.Sesion;
import control_calificaciones.models.usuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class RegistrarAlumnoController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    private Alert alert;

    String regexCurp = "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}" +
            "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" +
            "[HM]{1}" +
            "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
            "[B-DF-HJ-NP-TV-Z]{3}" +
            "[0-9A-Z]{1}[0-9]{1}$";

    String regexNombre = "[a-zA-ZÀ-ÖØ-öø-ÿ]+\\.?(( |\\-)[a-zA-ZÀ-ÖØ-öø-ÿ]+\\.?)*";

    String regexGmail = "[^@ \\t\\r\\n]+@gmail\\.com";

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        String sexos[] = { "H", "M" };
        optionSexoAlumno.getItems().addAll(sexos);
    }

    @FXML
    private DatePicker DateAlumnoFecha;

    @FXML
    private Button btnRegistrarAlumno;

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
    private TextField txtCurpAlumno;

    @FXML
    private TextField txtCorreoTutor2;

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

    public void iniciarSesion() {
        lblNombreUsuario.setText(Sesion.nombreUsuario);
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

    @FXML
    public void toPanelPrincipal(ActionEvent event) throws IOException {
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
    public void registrarAlumno(ActionEvent event) throws IOException {

        AlumnoDAO alumnoDAO = new AlumnoDAO();
        Alumno alumno = new Alumno();

        // captura de datos de la UI para el alumno
        String alumnoNombre = txtNombreAlumno.getText().trim();
        String alumnoApellidoP = txtApellidoPatAlumno.getText().trim();
        String alumnoApellidoM = txtApellidoMatAlumno.getText().trim();
        String alumnoCurp = txtCurpAlumno.getText().trim();
        String alumnoSexo = optionSexoAlumno.getSelectionModel().getSelectedItem();
        LocalDate alumnoFecha = DateAlumnoFecha.getValue();

        // validaciones de la interfaz
        if (alumnoNombre.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo nombre del alumno es requerido");
            alert.showAndWait();
            return;
        }

        if (!alumnoNombre.matches(regexNombre)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo nombre solo debe contener letras");
            alert.showAndWait();
            return;
        }

        if (alumnoApellidoP.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo apellido paterno del alumno es requerido");
            alert.showAndWait();
            return;
        }

        if (!alumnoApellidoP.matches(regexNombre)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo apellido paterno solo debe contener letras");
            alert.showAndWait();
            return;
        }

        if (alumnoApellidoM.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo apellido materno del alumno es requerido");
            alert.showAndWait();
            return;
        }

        if (!alumnoApellidoM.matches(regexNombre)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo apellido materno solo debe contener letras");
            alert.showAndWait();
            return;
        }

        if (alumnoCurp.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo curp del alumno es requerido");
            alert.showAndWait();
            return;
        }

        if (!alumnoCurp.matches(regexCurp)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("Formato invalido para la curp");
            alert.showAndWait();
            return;
        }

        if(!CurpValidador.coincideFecha(alumnoCurp, alumnoFecha)){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("la CURP no coincide con la fecha de nacimiento del alumno");
            alert.showAndWait();
            return;
        }
        
        if (alumnoSexo == null) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo sexo es requerido");
            alert.showAndWait();
            return;
        }

        if (alumnoFecha == null) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo de fecha de nacimiento del alumno es requerido");
            alert.showAndWait();
            return;
        }

        // capturando datos de alumnos validados desde la UI
        alumno.setCurp(alumnoCurp);
        alumno.setNombre(alumnoNombre);
        alumno.setApellido_paterno(alumnoApellidoP);
        alumno.setApellido_materno(alumnoApellidoM);
        alumno.setFecha_nacimiento(Date.valueOf(alumnoFecha));
        alumno.setGenero(alumnoSexo.charAt(0));

        // validaciones de la BD
        if (alumnoDAO.esNombreRepetido(alumno)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("Ya existe un alumno con el mismo nombre");
            alert.showAndWait();
            return;
        }

        if (alumnoDAO.esRepetido(alumno)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("La curp ya esta asignada para otro alumno");
            alert.showAndWait();
            return;
        }

        if (alumno.getEdad() < 6 || alumno.getEdad() > 12) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("La edad del alumno no es adecuada para el sistema");
            alert.showAndWait();

            txtGradoAlumno.setText("");
            txtGrupoAlumno.setText("");
            return;
        }

        // Empezamos a generar el grado y grupo
        String nombre_grado = "";
        AulaDAO aulaDAO = new AulaDAO();
        Aula aula = new Aula();
        ArrayList<Aula> aulas = new ArrayList<>();

        if (alumno.getEdad() == 6) {
            nombre_grado = "primero";
            aulas = aulaDAO.getAulasByNombreGrado(nombre_grado);
        }

        if (alumno.getEdad() == 7) {
            nombre_grado = "segundo";
            aulas = aulaDAO.getAulasByNombreGrado(nombre_grado);
        }

        if (alumno.getEdad() == 8) {
            nombre_grado = "tercero";
            aulas = aulaDAO.getAulasByNombreGrado(nombre_grado);
        }

        if (alumno.getEdad() == 9) {
            nombre_grado = "cuarto";
            aulas = aulaDAO.getAulasByNombreGrado(nombre_grado);
        }

        if (alumno.getEdad() == 10) {
            nombre_grado = "quinto";
            aulas = aulaDAO.getAulasByNombreGrado(nombre_grado);
        }

        if (alumno.getEdad() == 11 || alumno.getEdad() == 12) {
            nombre_grado = "sexto";
            aulas = aulaDAO.getAulasByNombreGrado(nombre_grado);
        }

        // aulas.removeIf(aulaDisponible -> aulaDisponible.getCantidad() >= 25);
        aulas.removeIf(aulaDisponible -> aulaDisponible.getCantidad() >= 25);

        if (aulas.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("ya no hay cupos para este grado");
            alert.showAndWait();
            return;
        }

        aula = aulas.get(0);
        txtGradoAlumno.setText(aula.getNombre_grado());
        txtGrupoAlumno.setText(aula.getNombre_grupo());

        // Empezamos a guardar/relacionar al tutor con el alumno

        // captura de datos de la UI para el tutor
        String tutorNombre = txtNombreTutor.getText().trim();
        String tutorApellidoP = txtApellidoPaTutor.getText().trim();
        String tutorApellidoM = txtMaTutor.getText().trim();
        String correo1 = txtCorreoTutor.getText().trim();
        String correo2 = txtCorreoTutor2.getText().trim();

        // validaciones de la UI para la creacion del tutor
        if (tutorNombre.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo de nombre del tutor es requerido");
            alert.showAndWait();
            return;
        }

        if (!tutorNombre.matches(regexNombre)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo nombre del tutor solo debe contener letras");
            alert.showAndWait();
            return;
        }

        if (tutorApellidoP.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo de apellido paterno del tutor es requerido");
            alert.showAndWait();
            return;
        }

        if (!tutorApellidoP.matches(regexNombre)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo apellido paterno del tutor solo debe contener letras");
            alert.showAndWait();
            return;
        }

        if (tutorApellidoM.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo de apellido materno del tutor es requerido");
            alert.showAndWait();
            return;
        }

        if (!tutorApellidoM.matches(regexNombre)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo apellido materno del tutor solo debe contener letras");
            alert.showAndWait();
            return;
        }

        if (correo1.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo de correo es requerido");
            alert.showAndWait();
            return;
        }

        if (!correo1.matches(regexGmail)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("Es necesario agregar un correo valido para el correo obligatorio");
            alert.showAndWait();
            return;
        }

        if (!correo2.matches(regexGmail) && !correo2.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("Es necesario agregar un correo valido para el correo opcional");
            alert.showAndWait();
            return;
        }

        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutor = new Tutor();
        tutor.setNombre(tutorNombre);
        tutor.setApellido_paterno(tutorApellidoP);
        tutor.setApellido_materno(tutorApellidoM);

        // validaciones desde la BD de tutor
        if (tutorDAO.esRepetido(tutor)) {
            tutor = tutorDAO.buscarByNombreCompleto(tutor);
            // alumno.setId_tutor(tutor.getId_tutor());

            correo1 = tutorDAO.getCorreos(tutor).get(0).getCorreo();
            txtCorreoTutor.setText(correo1);
            txtCorreoTutor.setDisable(true);

            if (tutorDAO.getCorreos(tutor).size() > 1) {
                correo2 = tutorDAO.getCorreos(tutor).get(1).getCorreo();
                txtCorreoTutor2.setText(correo2);
                txtCorreoTutor2.setDisable(true);
            }
        }
        // El tutor no esta registrado en el sistema y tenemos que crearlo
        else {

            // agregar correo o correos (maximo 2)
            if (!correo1.trim().isEmpty() && CorreoTutorDAO.esRepetido(correo1)) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Mensaje");
                alert.setContentText("El correo " + correo1 + " ya esta en uso");
                alert.showAndWait();
                return;
            }

            if (!correo2.trim().isEmpty() && CorreoTutorDAO.esRepetido(correo2)) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Mensaje");
                alert.setContentText("El correo " + correo2 + " ya esta en uso");
                alert.showAndWait();
                return;
            }

            tutorDAO.insertar(tutor);
            tutor = tutorDAO.buscarByNombreCompleto(tutor);

            if (!correo1.isEmpty()) {
                tutorDAO.insertarCorreo(tutor, correo1);
            }

            if (!correo2.isEmpty()) {
                tutorDAO.insertarCorreo(tutor, correo2);
            }
        }

        // objeto del alumno a guardar en la BD
        alumno.setId_aula(aula.getId_aula());
        alumno.setId_tutor(tutor.getId_tutor());
        alumnoDAO.insertar(alumno);

        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setContentText("Alumno agregado perfectamente");
        alert.showAndWait();

        //limpiando los datos
        txtNombreAlumno.setText("");
        txtApellidoMatAlumno.setText("");
        txtApellidoPatAlumno.setText("");

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
    }

}
