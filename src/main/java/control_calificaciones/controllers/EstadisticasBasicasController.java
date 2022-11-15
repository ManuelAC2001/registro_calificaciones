package control_calificaciones.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import control_calificaciones.App;
import control_calificaciones.data.AulaDAOH;
import control_calificaciones.data.usuarios.DirectorDAO;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.helpers.estadisticas.alumnos.EstadisticaBasica;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EstadisticasBasicasController implements Initializable {

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
    private TableColumn<EstadisticaBasica, Integer> columnTotalGrado;

    @FXML
    private TextField txtTotalAlumnos;

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

        Integer totalAlumnos = aulas.stream().map(a -> a.getAlumnos().size()).mapToInt(Integer::valueOf).sum();
        txtTotalAlumnos.setText(totalAlumnos.toString());

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

        tableGrado.setItems( FXCollections.observableArrayList(estadisticasGrados));

    }

}
