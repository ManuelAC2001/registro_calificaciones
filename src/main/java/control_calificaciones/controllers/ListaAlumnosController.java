package control_calificaciones.controllers;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control_calificaciones.App;
import control_calificaciones.data.AlumnoDAO;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.helpers.pdf.ListaPDF;
import control_calificaciones.models.Alumno;
import control_calificaciones.models.usuarios.Asignatura;
import control_calificaciones.models.usuarios.Sesion;
import control_calificaciones.models.usuarios.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ListaAlumnosController implements Initializable{
    
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TableColumn<Alumno, String> columnCurp;

    @FXML
    private TableColumn<Alumno, String> columnNombre;

    @FXML
    private TableColumn<Alumno, String> columnApellidoP;

    @FXML
    private TableColumn<Alumno, String> columnApellidoM;

    @FXML
    private TableColumn<Alumno, Character> columnSexo;

    @FXML
    private TableColumn<Alumno, String> columnGrado;

    @FXML
    private TableColumn<Alumno, String> columnGrupo;

    @FXML
    private ComboBox<String> comboGrado;

    @FXML
    private ComboBox<String> comboGrupo;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private TableView<Alumno> tablaUsuarios;

    public ArrayList<Alumno> alumnos = new ArrayList<>();

    public void iniciarSesion(){
        lblNombreUsuario.setText(Sesion.nombreUsuario);
    }

    @FXML
    private void buscarAlumnos(ActionEvent event) throws IOException {

        Alert alert;

        //validar los comboBox
        String gradoSeleccionado = comboGrado.getSelectionModel().getSelectedItem();
        String grupoSeleccionado = comboGrupo.getSelectionModel().getSelectedItem();

        if(gradoSeleccionado == null || grupoSeleccionado == null){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El campo nombre del alumno es requerido");
            alert.showAndWait();
            return;
        }

        alumnos = new AlumnoDAO().getListaAlumnosByAula(gradoSeleccionado, grupoSeleccionado);

        if(alumnos.isEmpty()){
            tablaUsuarios.getItems().clear();
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("El salon aun no cuenta con alumnos");
            alert.showAndWait();
            return;
        }

        columnCurp.setCellValueFactory(new PropertyValueFactory<Alumno, String>("curp"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
        columnApellidoP.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellido_paterno"));
        columnApellidoM.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellido_materno"));
        columnSexo.setCellValueFactory(new PropertyValueFactory<Alumno, Character>("genero"));
        columnGrado.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre_grado"));
        columnGrupo.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre_grupo"));

        ObservableList<Alumno> listaAlumnos = FXCollections.observableArrayList(alumnos);
        tablaUsuarios.setItems(listaAlumnos);
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
    private void generarPDF(ActionEvent event) throws IOException {

        if(alumnos.isEmpty()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("No puedes generar un archivo PDF de un salon sin alumnos");
            alert.showAndWait();
            return;
        }


        String nombreGrado = alumnos.get(0).getNombre_grado();
        ArrayList<Asignatura> asignaturas = new  AlumnoDAO().getAsignaturas(nombreGrado); 

        String workPath = System.getProperty("user.dir");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(workPath));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Acción cancelada");
            alert.setContentText("la lista de alumnos no se guardo");
            alert.showAndWait();
            return;
        }

        ListaPDF.listaHTMLPDF(file, alumnos, asignaturas);

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Acción cancelada");
        alert.setContentText("la lista de alumnos se guardo correctamente");
        alert.showAndWait();
    }

    @FXML
    private void toSeccionPrincipal(ActionEvent event) throws IOException {

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

        lblNombreUsuario.setText(Sesion.nombreUsuario);

        String grados[] = { "primero", "segundo", "tercero", "cuarto", "quinto", "sexto" };
        comboGrado.getItems().setAll(grados);
        
        String grupos[] = { "A", "B", "C"};
        comboGrupo.getItems().setAll(grupos);

        
    }

}
