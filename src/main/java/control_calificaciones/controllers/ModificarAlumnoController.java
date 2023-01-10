package control_calificaciones.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import control_calificaciones.App;
import control_calificaciones.data.AlumnoDAO;
import control_calificaciones.data.CorreoTutorDAOH;
import control_calificaciones.data.TutorDAO;
import control_calificaciones.data.TutorDAOH;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.models.Alumno;
import control_calificaciones.models.Aula;
import control_calificaciones.models.CorreoTutor;
import control_calificaciones.models.CorreoTutorH;
import control_calificaciones.models.Tutor;
import control_calificaciones.models.TutorH;
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

public class ModificarAlumnoController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    private Alumno alumno;
    private Alert alert;

    String regexCurp = "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}" +
            "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" +
            "[HM]{1}" +
            "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
            "[B-DF-HJ-NP-TV-Z]{3}" +
            "[0-9A-Z]{1}[0-9]{1}$";

    String regexNombre = "[a-zA-ZÀ-ÖØ-öø-ÿ]+\\.?(( |\\-)[a-zA-ZÀ-ÖØ-öø-ÿ]+\\.?)*";

    String regexGmail = "[^@ \\t\\r\\n]+@gmail\\.com";
    String regexHotmail = "[^@ \\t\\r\\n]+@hotmail\\.com";
    String regexYahoo = "[^@ \\t\\r\\n]+@yahoo\\.com";
    String regexTecMail = "[^@ \\t\\r\\n]+@acapulco.tecnm.mx\\.com";

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

    public void iniciarSesion() {
        lblNombreUsuario.setText(Sesion.nombreUsuario);
    }

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

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El alumno a modificar no existe");
            alert.showAndWait();

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
        String correo1 = txtCorreoTutor.getText().trim();
        String correo2 = txtCorreoTutor2.getText().trim();

        // validando datos de alumno desde la UI
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

        if (correo1.equalsIgnoreCase(correo2)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("No es posible tener el mismo correo dos veces");
            alert.showAndWait();
            return;
        }

        if (!correo1.matches(regexGmail) && !correo1.matches(regexHotmail) && !correo1.matches(regexYahoo)
                && !correo1.matches(regexTecMail)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("Es necesario agregar un correo valido para el correo obligatorio");
            alert.showAndWait();
            return;
        }

        if (!correo2.matches(regexGmail) && !correo2.matches(regexHotmail) && !correo2.matches(regexYahoo)
                && !correo2.matches(regexTecMail) && !correo2.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("Es necesario agregar un correo valido para el correo opcional");
            alert.showAndWait();
            return;
        }

        AlumnoDAO alumnoDAO = new AlumnoDAO();
        TutorDAO tutorDAO = new TutorDAO();
        TutorDAOH tutorDAOH = new TutorDAOH();
        CorreoTutorDAOH correoDAO = new CorreoTutorDAOH();

        Tutor tutor = tutorDAO.buscarById(alumno.getId_tutor());
        TutorH tutorH = new TutorH(alumno.getId_tutor());
        tutorH = tutorDAOH.buscarPorId(tutorH);

        List<CorreoTutorH> correosTutor = tutorH.getCorreos();
        CorreoTutorH correoTutor = correosTutor.get(0);
        CorreoTutorH correoTutor2 = null;

        

        // empezando la modificacion del alumno
        alumno.setNombre(alumnoNombre);
        alumno.setApellido_paterno(alumnoApellidoP);
        alumno.setApellido_materno(alumnoApellidoM);
        alumno.setGenero(alumnoSexo.charAt(0));

        // empezando la modificacion del tutor
        tutor.setNombre(tutorNombre);
        tutor.setApellido_paterno(tutorApellidoP);
        tutor.setApellido_materno(tutorApellidoM);
        correoTutor.setCorreo(correo1);

        if (correosTutor.size() > 1) {
            correoTutor2 = tutorH.getCorreos().get(1);
            correoTutor2.setCorreo(correo2);
        }



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

        if (correoDAO.buscarPorCorreo(correoTutor) != null) {

            TutorH tutorEncontrado = correoDAO.buscarPorCorreo(correoTutor).getTutor();

            if (!tutorEncontrado.equals(tutorH)) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Mensaje");
                alert.setContentText("El correo ya esta en uso");
                alert.showAndWait();
                return;
            }

        }

        if (correoTutor2 != null) {

            CorreoTutorH correoEncontrado = correoDAO.buscarPorCorreo(correoTutor2);

            if(correoEncontrado != null) {
                TutorH tutorEncontrado = correoEncontrado.getTutor();


                if (tutorEncontrado != null && !tutorEncontrado.equals(tutorH) ) {

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Mensaje");
                    alert.setContentText("El correo ya esta en uso");
                    alert.showAndWait();
                    return;
                }
            }
        }


        tutorDAO.modificar(tutor);
        alumnoDAO.modificar(alumno);
        correoDAO.actualizar(correoTutor);

        if(correoTutor2 == null){

            correoTutor2 = new CorreoTutorH();
            correoTutor2.setCorreo(correo2);
            correoTutor2.setTutor(tutorH);;

            CorreoTutorH correoEncontrado = correoDAO.buscarPorCorreo(correoTutor2);

            if(correoEncontrado != null){

                TutorH tutorEncontrado = correoEncontrado.getTutor();

                if (tutorEncontrado != null && !tutorEncontrado.equals(tutorH) ) {

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Mensaje");
                    alert.setContentText("El correo ya esta en uso");
                    alert.showAndWait();
                    return;
                }
                correoDAO.insertar(correoTutor2); 
            }

        }

        

        // // validaciones de insercion del segundo correo
        // if (correoTutor2 == null) {
        //     correoTutor2 = new CorreoTutorH();
        //     correoTutor2.setTutor(tutorH);
        //     correoTutor2.setCorreo(correo2);
        //     correoDAO.insertar(correoTutor2);
        // }

        if (correoTutor2 != null && !correo2.isEmpty()) {
            correoTutor2.setCorreo(correo2);
            correoDAO.actualizar(correoTutor2);
        }

        if (correoTutor2 != null && correo2.isEmpty()) {
            correoDAO.eliminar(correoTutor2);
        }

        // acceder a la siguiente ventana
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setContentText("Alumno modificado perfectamente");
        alert.showAndWait();

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        String sexos[] = { "H", "M" };
        optionSexoAlumno.getItems().addAll(sexos);
    }

}
