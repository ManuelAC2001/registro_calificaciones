package control_calificaciones.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control_calificaciones.data.AlumnoDAO;
import control_calificaciones.data.TutorDAO;
import control_calificaciones.models.Alumno;
import control_calificaciones.models.Aula;
import control_calificaciones.models.CorreoTutor;
import control_calificaciones.models.Tutor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ModificarAlumnoController implements Initializable {

    private Alumno alumno;

    private Alert alert;

    @FXML
    private DatePicker DateAlumnoFecha;

    @FXML
    private ComboBox<String> optionSexoAlumno;

    @FXML
    private Button btnModificarAlumno;

    @FXML
    private Label lblNombreUsuario;

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
    private void buscar(ActionEvent event) throws IOException {

        // caputrando el nombre completo del alumno desde la UI
        String alumnoNombre = txtNombreAlumno.getText().trim();
        String alumnoApellidoP = txtApellidoPatAlumno.getText().trim();
        String alumnoApellidoM = txtApellidoMatAlumno.getText().trim();

        // validacion de la UI
        // validaciones de la interfaz
        if (alumnoNombre.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo nombre del alumno es requerido");
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

        if (alumnoApellidoM.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo apellido materno del alumno es requerido");
            alert.showAndWait();
            return;
        }

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

            btnModificarAlumno.setDisable(true);

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El alumno a modificar no existe");
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

        btnModificarAlumno.setDisable(false);

    }

    @FXML
    private void modificarAlumno(ActionEvent event) throws IOException {

        if (alumno == null) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El alumno a modificar no existe");
            alert.showAndWait();
            return;
        }

        // tomando datos de la UI para los datos del alumno
        String alumnoNombre = txtNombreAlumno.getText().trim();
        String alumnoApellidoP = txtApellidoPatAlumno.getText().trim();
        String alumnoApellidoM = txtApellidoMatAlumno.getText().trim();
        String alumnoSexo = optionSexoAlumno.getSelectionModel().getSelectedItem();

        // tomando datos de la UI para los datos del tutor
        String tutorNombre = txtNombreTutor.getText().trim();
        String tutorApellidoP = txtApellidoPaTutor.getText().trim();
        String tutorApellidoM = txtMaTutor.getText().trim();

        AlumnoDAO alumnoDAO = new AlumnoDAO();
        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutor = tutorDAO.buscarById(alumno.getId_tutor());

        // empezando la modificacion del alumno
        alumno.setNombre(alumnoNombre);
        alumno.setApellido_paterno(alumnoApellidoP);
        alumno.setApellido_materno(alumnoApellidoM);
        alumno.setGenero(alumnoSexo.charAt(0));

        // empezando la modificacion del tutor
        tutor.setNombre(tutorNombre);
        tutor.setApellido_paterno(tutorApellidoP);
        tutor.setApellido_materno(tutorApellidoM);

        if (alumnoDAO.esNombreRepetido(alumno)) {

            if (!alumno.getCurp().equalsIgnoreCase(alumnoDAO.buscarByNombreCompleto(alumno).getCurp())) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Mensaje");
                alert.setContentText("Ya existe un alumno con el mismo nombre");
                alert.showAndWait();
                return;
            }
        }

        // una vez modificado los datos, verifica que no coincida con un tutor anterior
        if (tutorDAO.esRepetido(tutor)) {

            if (tutor.getId_tutor() != tutorDAO.buscarByNombreCompleto(tutor).getId_tutor()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Mensaje");
                alert.setContentText("Ya existe un tutor con el mismo nombre");
                alert.showAndWait();
                return;
            }
        }

        tutorDAO.modificar(tutor);
        alumnoDAO.modificar(alumno);

        //acceder a la siguiente ventana
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setContentText("Alumno modificado perfectamente");
        alert.showAndWait();

    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {

    }

    @FXML
    private void toPanelPrincipal(ActionEvent event) throws IOException {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        String sexos[] = { "H", "M" };
        optionSexoAlumno.getItems().addAll(sexos);
    }

}
