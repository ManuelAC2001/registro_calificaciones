package control_calificaciones.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import control_calificaciones.App;
import control_calificaciones.data.Conexion;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.helpers.pdf.GenerarPDF;
import control_calificaciones.models.BitacoraSesion;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class BitacoraSesionController implements Initializable {

    private ObservableList<BitacoraSesion> listaUsuarios;

    @FXML
    private TableView<BitacoraSesion> tablaUsuarios;

    @FXML
    private TableColumn<BitacoraSesion, Integer> columnAnio;

    @FXML
    private TableColumn<BitacoraSesion, Integer> columnDia;

    @FXML
    private TableColumn<BitacoraSesion, String> columnHoraEntrada;

    @FXML
    private TableColumn<BitacoraSesion, String> columnHoraSalida;

    @FXML
    private TableColumn<BitacoraSesion, String> columnMes;

    @FXML
    private TableColumn<BitacoraSesion, String> columnNombreUsuario;

    @FXML
    private Label lblNombreUsuario;

    private Parent root;
    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        String procedureCall = "{CALL getbitacoraSesionUsuarios()}";
        CallableStatement cstmt = null;
        Connection cn = null;
        ResultSet rs = null;
        listaUsuarios = FXCollections.observableArrayList();

        lblNombreUsuario.setText(Sesion.nombreUsuario);

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(procedureCall);
            rs = cstmt.executeQuery();

            while (rs.next()) {
                BitacoraSesion bSesion = new BitacoraSesion();
                bSesion.setNombreUsuario(rs.getString(2));
                bSesion.setDia(rs.getInt(3));
                bSesion.setAnio(rs.getInt(4));
                bSesion.setMes(rs.getString(5));
                bSesion.setHoraEntrada(rs.getString(6));
                bSesion.setHoraSalida(rs.getString(7));
                listaUsuarios.add(bSesion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(cstmt);
            Conexion.close(cn);
        }

        columnNombreUsuario.setCellValueFactory(new PropertyValueFactory<BitacoraSesion, String>("nombreUsuario"));
        columnDia.setCellValueFactory(new PropertyValueFactory<BitacoraSesion, Integer>("dia"));
        columnMes.setCellValueFactory(new PropertyValueFactory<BitacoraSesion, String>("mes"));
        columnAnio.setCellValueFactory(new PropertyValueFactory<BitacoraSesion, Integer>("anio"));
        columnHoraEntrada.setCellValueFactory(new PropertyValueFactory<BitacoraSesion, String>("horaEntrada"));
        columnHoraSalida.setCellValueFactory(new PropertyValueFactory<BitacoraSesion, String>("horaSalida"));

        tablaUsuarios.setItems(listaUsuarios);

        System.out.println(Sesion.fechaSesion);

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
    public void toSeccionPrincipal(ActionEvent event) throws IOException {

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
    public void generarPDF(ActionEvent event) throws IOException {

        String workPath = System.getProperty("user.dir");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(workPath));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Acción cancelada");
            alert.setContentText("la bitacora no se guardo");
            alert.showAndWait();
            return;
        }

        GenerarPDF.generarPDF(file);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Acción exitosa");
        alert.setContentText("la bitacora se guardo correctamente");
        alert.showAndWait();
    }

}
