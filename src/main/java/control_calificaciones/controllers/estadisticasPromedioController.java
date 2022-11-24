package control_calificaciones.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import control_calificaciones.App;
import control_calificaciones.data.AulaDAOH;
import control_calificaciones.data.usuarios.*;
import control_calificaciones.helpers.estadisticas.alumnos.EstadisticaPromedio;
import control_calificaciones.helpers.pdf.GenerarPDF;
import control_calificaciones.models.AulaH;
import control_calificaciones.models.usuarios.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class estadisticasPromedioController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    // sesion
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
    private TableView<EstadisticaPromedio> tableEstadistica;

    @FXML
    private TableColumn<EstadisticaPromedio, String> columnAlumno;

    @FXML
    private TableColumn<EstadisticaPromedio, String> columnGrado;

    @FXML
    private TableColumn<EstadisticaPromedio, String> columnGrupo;

    @FXML
    private TableColumn<EstadisticaPromedio, Double> columnPromedioGeneral;

    @FXML
    private TableColumn<EstadisticaPromedio, Double> columnPromedioMax;

    @FXML
    private TextField txtPromedioEscuela;

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
    void toPanelPrincipal(ActionEvent event) throws IOException {

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

    public void iniciarSesion() {
        nombreUsuario = Sesion.nombreUsuario;
        lblNombreUsuario.setText(Sesion.nombreUsuario);
    }

    @FXML
    public void generarPDF(ActionEvent event) {

        String workPath = System.getProperty("user.dir");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(workPath));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Acción cancelada");
            alert.setContentText("La estadistica basica no se generaron");
            alert.showAndWait();
            return;
        }

        List<EstadisticaPromedio> estadisticas = aulas.stream()
                .map(a -> new EstadisticaPromedio(a))
                .collect(Collectors.toList());


        GenerarPDF.generarEstadisticasPromedio(file, estadisticas);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Acción exitosa");
        alert.setContentText("La estadistica basica se guardo correctamente");
        alert.showAndWait();

        

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        String promedioEscuela = String.format("%.2f", EstadisticaPromedio.getPromedioEscuela());
        txtPromedioEscuela.setText(promedioEscuela);

        List<EstadisticaPromedio> estadisticas = aulas.stream()
                .map(a -> new EstadisticaPromedio(a))
                .collect(Collectors.toList());

        columnGrado.setCellValueFactory(new PropertyValueFactory<EstadisticaPromedio, String>("gradoNombre"));
        columnGrupo.setCellValueFactory(new PropertyValueFactory<EstadisticaPromedio, String>("grupoNombre"));
        columnAlumno.setCellValueFactory(new PropertyValueFactory<EstadisticaPromedio, String>("nombreAlumnoPromedioMaximo"));
        columnPromedioMax.setCellValueFactory(new PropertyValueFactory<EstadisticaPromedio, Double>("promedioMaximo"));
        columnPromedioGeneral.setCellValueFactory(new PropertyValueFactory<EstadisticaPromedio, Double>("promedioGeneral"));
        
        tableEstadistica.setItems(FXCollections.observableList(estadisticas));

    }

}
