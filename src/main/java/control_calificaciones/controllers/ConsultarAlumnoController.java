package control_calificaciones.controllers;

import java.io.IOException;
import java.util.ArrayList;

import control_calificaciones.data.AlumnoDAO;
import control_calificaciones.data.TutorDAO;
import control_calificaciones.models.Alumno;
import control_calificaciones.models.Aula;
import control_calificaciones.models.CorreoTutor;
import control_calificaciones.models.Tutor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ConsultarAlumnoController {

    private Alert alert;
    
    @FXML
    private DatePicker DateAlumnoFecha;

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

    }

    @FXML
    private void toPanelPrincipal(ActionEvent event) throws IOException {

    }

    @FXML
    public void buscar(ActionEvent event) throws IOException {

        //capturando datos de la UI
        String alumnoNombre = txtNombreAlumno.getText().trim();
        String alumnoApellidoP = txtApellidoPatAlumno.getText().trim();
        String alumnoApellidoM = txtApellidoMatAlumno.getText().trim();

        Alumno alumno = new Alumno();
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutor = new Tutor();

        ArrayList<CorreoTutor> correosTutor = new ArrayList<>();

        alumno.setNombre(alumnoNombre);
        alumno.setApellido_paterno(alumnoApellidoP);
        alumno.setApellido_materno(alumnoApellidoM);

        alumno = alumnoDAO.buscarByNombreCompleto(alumno);

        if(alumnoNombre.isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo nombre de alumno es requerido");
            alert.showAndWait();
            return;
        }

        if(alumnoApellidoP.isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo apellido paterno de alumno es requerido");
            alert.showAndWait();
            return;
        }

        if(alumnoApellidoM.isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo apellido materno de alumno es requerido");
            alert.showAndWait();
            return;
        }
        
        if(alumno == null){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El alumno no existe");
            alert.showAndWait();
            return;
        }

        tutor = tutorDAO.buscarById(alumno.getId_tutor());
        correosTutor = tutorDAO.getCorreos(tutor);

        //ESTABLECIENDO LA INFORMACION EN LA UI
        txtCurpAlumno.setText(alumno.getCurp());
        optionSexoAlumno.setValue(alumno.getGenero().toString());
        DateAlumnoFecha.setValue(alumno.getFecha_nacimiento().toLocalDate());

        //grado y grupo queda pendiente
        Aula aula = alumnoDAO.getAula(alumno);
        txtGradoAlumno.setText(aula.getNombre_grado());
        txtGrupoAlumno.setText(aula.getNombre_grupo());

        //TOMANDO DATOS DEL TUTOR
        txtNombreTutor.setText(tutor.getNombre());
        txtApellidoPaTutor.setText(tutor.getApellido_paterno());
        txtMaTutor.setText(tutor.getApellido_materno());
        
        //TOMANDO DATOS DE LOS CORREOS DEL TUTOR
        txtCorreoTutor.setText( correosTutor.get(0).getCorreo() );
        
        if(correosTutor.size() > 1){
            txtCorreoTutor2.setText( correosTutor.get(1).getCorreo() );
        }

    }

}
