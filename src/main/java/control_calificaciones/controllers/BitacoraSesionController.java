package control_calificaciones.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import control_calificaciones.data.Conexion;
import control_calificaciones.helpers.pdf.GenerarPDF;
import control_calificaciones.models.BitacoraSesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        String procedureCall = "{CALL getbitacoraSesionUsuarios()}";
        CallableStatement cstmt = null;
        Connection cn = null;
        ResultSet rs = null;
        listaUsuarios = FXCollections.observableArrayList();

        try {
            cn = Conexion.getConnection();
            cstmt = cn.prepareCall(procedureCall);
            rs = cstmt.executeQuery();

            while(rs.next()){
                BitacoraSesion bSesion = new BitacoraSesion();
                bSesion.setNombreUsuario(rs.getString(2));
                bSesion.setDia(rs.getInt(3));
                bSesion.setAnio(rs.getInt(4));
                bSesion.setMes(rs.getString(5));
                bSesion.setHoraEntrada(rs.getString(6));
                bSesion.setHoraSalida(rs.getString(7));
                listaUsuarios.add(bSesion);
            }

        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
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

    }

    @FXML
    public void generarPDF(ActionEvent event) throws IOException {

        //GenerarPDF.generarPDF();

    }

}
