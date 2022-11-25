package control_calificaciones.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import control_calificaciones.App;
import control_calificaciones.data.AulaDAOH;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.helpers.estadisticas.alumnos.EstadisticaBasica;
import control_calificaciones.helpers.pdf.GenerarPDF;
import control_calificaciones.models.AulaH;
import control_calificaciones.models.GradoH;
import control_calificaciones.models.usuarios.Sesion;
import control_calificaciones.models.usuarios.Usuario;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class EstadisticasBasicasController implements Initializable {

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
    private TableView<EstadisticaBasica> tableEstadistica;

    @FXML
    private TableView<EstadisticaBasica> tableGrado;
    
    @FXML
    private TableColumn<EstadisticaBasica, String> columnGrado;

    @FXML
    private TableColumn<EstadisticaBasica, String> columnGrupo;

    @FXML
    private TableColumn<EstadisticaBasica, Integer> columnHombres;

    @FXML
    private TableColumn<EstadisticaBasica, Integer> columnMujeres;

    @FXML
    private TableColumn<EstadisticaBasica, Integer> columnTotal;

    @FXML
    private TableColumn<EstadisticaBasica, String> columnGradoNombre;

    @FXML
    private TableColumn<EstadisticaBasica, Integer> columnHombresGrado;

    @FXML
    private TableColumn<EstadisticaBasica, Integer> columnMujeresGrado;

    @FXML
    private TableColumn<EstadisticaBasica, Integer> columnTotalGrado;

    @FXML
    private TextField txtTotalAlumnos;

    @FXML
    private TextField txtTotalHombres;

    @FXML
    private TextField txtTotalMujeres;

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

        List<EstadisticaBasica> estadisticas = aulas.stream().map(a -> new EstadisticaBasica(a)).collect(Collectors.toList());

        List<GradoH> grados = aulas.stream().map(a -> a.getGrado()).distinct().collect(Collectors.toList());
        List<EstadisticaBasica> estadisticasGrados = grados.stream().map( g -> new EstadisticaBasica(g)).collect(Collectors.toList());  
        Integer totalAlumnos = aulas.stream().map(a -> a.getAlumnos().size()).mapToInt(Integer::valueOf).sum();
        

        GenerarPDF.generarEstadisticasBasicas(file, estadisticas, estadisticasGrados, totalAlumnos);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Acción exitosa");
        alert.setContentText("La estadistica basica se guardo correctamente");
        alert.showAndWait();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        List<EstadisticaBasica> estadisticas = aulas.stream().map(a -> new EstadisticaBasica(a)).collect(Collectors.toList());  

        columnGrado.setCellValueFactory(new PropertyValueFactory<EstadisticaBasica, String>("gradoNombre"));
        columnGrupo.setCellValueFactory(new PropertyValueFactory<EstadisticaBasica, String>("grupoNombre"));
        columnHombres.setCellValueFactory(new PropertyValueFactory<EstadisticaBasica, Integer>("cantidadHombres"));
        columnMujeres.setCellValueFactory(new PropertyValueFactory<EstadisticaBasica, Integer>("cantidadMujeres"));
        columnTotal.setCellValueFactory(new PropertyValueFactory<EstadisticaBasica, Integer>("cantidadAlumnosTotal"));

        tableEstadistica.setItems(FXCollections.observableArrayList(estadisticas));

        List<GradoH> grados = aulas.stream().map(a -> a.getGrado()).distinct().collect(Collectors.toList());

        List<EstadisticaBasica> estadisticasGrados = grados.stream().map( g -> new EstadisticaBasica(g)).collect(Collectors.toList());
        
        columnGradoNombre.setCellValueFactory( new PropertyValueFactory<EstadisticaBasica, String>("gradoNombre"));        
        columnTotalGrado.setCellValueFactory( new PropertyValueFactory<EstadisticaBasica, Integer>("cantidadAlumnosGrado"));
        columnHombresGrado.setCellValueFactory( new PropertyValueFactory<EstadisticaBasica, Integer>("cantidadHombresGrado") );
        columnMujeresGrado.setCellValueFactory( new PropertyValueFactory<EstadisticaBasica, Integer>("cantidadMujeresGrado") );

        tableGrado.setItems( FXCollections.observableArrayList(estadisticasGrados));

        Integer cantidadAlumnosHombres = EstadisticaBasica.alumnos.stream().filter(a -> a.getGenero().equals('H')).collect(Collectors.toList()).size();
        Integer cantidadAlumnosMujeres = EstadisticaBasica.alumnos.stream().filter(a -> a.getGenero().equals('M')).collect(Collectors.toList()).size();

        txtTotalMujeres.setText(cantidadAlumnosMujeres.toString());
        txtTotalHombres.setText(cantidadAlumnosHombres.toString());

        Integer totalAlumnos = aulas.stream().map(a -> a.getAlumnos().size()).mapToInt(Integer::valueOf).sum();
        txtTotalAlumnos.setText(totalAlumnos.toString());

    }

}
