package control_calificaciones.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import control_calificaciones.App;
import control_calificaciones.data.AlumnoDAOH;
import control_calificaciones.data.CalificacionDAOH;
import control_calificaciones.data.CicloEscolarDAOH;
import control_calificaciones.data.InasistenciaDAOH;
import control_calificaciones.data.MesDAOH;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.helpers.pdf.BoletaExterna;
import control_calificaciones.helpers.pdf.BoletaInterna;
import control_calificaciones.models.AlumnoH;
import control_calificaciones.models.AsignaturaH;
import control_calificaciones.models.CalificacionH;
import control_calificaciones.models.CicloEscolarH;
import control_calificaciones.models.InasistenciaH;
import control_calificaciones.models.MesH;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ModificacionCalificacionesController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;
    private Alert alert;

    private String regexCalificacion = "^(\\d{1}\\.)?(\\d+\\.?)+(,\\d{2})?$";
    private String regexInasistencias = "^[0-9]+$";

    @FXML
    private Button btnModificarCalificacion;

    @FXML
    private Label lblAsignatura1;

    @FXML
    private Label lblAsignatura2;

    @FXML
    private Label lblAsignatura3;

    @FXML
    private Label lblAsignatura4;

    @FXML
    private Label lblAsignatura5;

    @FXML
    private Label lblAsignatura6;

    @FXML
    private Label lblAsignatura7;

    @FXML
    private Label lblAsignatura8;

    @FXML
    private Label lblAsignatura9;

    @FXML
    private Label lblAsignatura10;

    @FXML
    private Label lblAsignatura11;

    @FXML
    private Label lblAsignatura12;

    @FXML
    private Label lblAsignatura13;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private TextField txtApellidoMatAlumno;

    @FXML
    private TextField txtApellidoPatAlumno;

    @FXML
    private TextField txtGradoAlumno;

    @FXML
    private TextField txtGrupoAlumno;

    @FXML
    private TextField txtNombreAlumno;

    @FXML
    private TextField txtCalificacion1;
    @FXML
    private TextField txtCalificacion2;
    @FXML
    private TextField txtCalificacion3;
    @FXML
    private TextField txtCalificacion4;
    @FXML
    private TextField txtCalificacion5;
    @FXML
    private TextField txtCalificacion6;
    @FXML
    private TextField txtCalificacion7;
    @FXML
    private TextField txtCalificacion8;
    @FXML
    private TextField txtCalificacion9;
    @FXML
    private TextField txtCalificacion10;
    @FXML
    private TextField txtCalificacion11;
    @FXML
    private TextField txtCalificacion12;
    @FXML
    private TextField txtCalificacion13;

    @FXML
    private TextField txtInasistencias;

    @FXML
    private Button btnBoletaExterna;

    @FXML
    private Button btnBoletaInterna;

    private List<Label> lblMateriasAcademicas = new ArrayList<>();
    private List<Label> lblMateriasComplementarias = new ArrayList<>();

    private List<TextField> txtMateriasAcademicas = new ArrayList<>();
    private List<TextField> txtMateriasComplementarias = new ArrayList<>();
    private List<TextField> txtMateriasGeneral = new ArrayList<>();

    private CicloEscolarH cicloEscolar;
    private MesH mesSeleccionado;
    private AlumnoH alumno;

    @FXML
    private ComboBox<String> cmbMes;

    @FXML
    private void buscar(ActionEvent event) {

        limpiarUI();

        AlumnoDAOH alumnoDAO = new AlumnoDAOH();
        alumno = new AlumnoH();
        alumno.setNombre(txtNombreAlumno.getText().trim());
        alumno.setApellidoPaterno(txtApellidoPatAlumno.getText().trim());
        alumno.setApellidoMaterno(txtApellidoMatAlumno.getText().trim());

        alumno = alumnoDAO.buscarNombre(alumno);

        if (alumno == null) {
            cmbMes.setDisable(true);
            btnModificarCalificacion.setDisable(true);
            btnBoletaInterna.setDisable(true);
            btnBoletaExterna.setDisable(true);
            txtInasistencias.setVisible(false);

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("Alumno no encontrado");
            alert.showAndWait();
            return;
        }

        txtInasistencias.setVisible(true);
        btnBoletaInterna.setDisable(false);
        btnBoletaExterna.setDisable(false);
        cmbMes.setDisable(false);

        // asignamos grado y grupo
        txtGrupoAlumno.setText(alumno.getAula().getGrupo().getNombre());
        txtGradoAlumno.setText(alumno.getAula().getGrado().getNombre());

        // creamos la interfaz grafica de llenado de calificacioenes por materia
        llenarAsignaturasColumns(alumno.getAula().getGrado().getAsignaturas());

    }

    @FXML
    private void generarBoletaExterna(ActionEvent event) {

        if (alumno.getCalificaciones().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se puede generar la boleta externa");
            alert.setContentText("El alumno aun no tiene calificaciones registradas");
            alert.showAndWait();
            return;
        }

        List<MesH> meses = new MesDAOH().listar();
        Boolean esBoletaOficial = false;

        for (MesH mes : meses) {
            if (!esMesCalificado(alumno, mes.getNombre())) {
                esBoletaOficial = false;
                continue;
            }
            esBoletaOficial = true;
        }

        // Manipulando el fileChooser
        String workPath = System.getProperty("user.dir");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(workPath));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Acción cancelada");
            alert.setContentText("la boleta externa no se guardo");
            alert.showAndWait();
            return;
        }

        // capturamos la calificacion por mes
        List<CalificacionH> calificacionesBoleta = getCalificacionesActuales();
        BoletaExterna.generarPDF(file, calificacionesBoleta, esBoletaOficial);

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Acción cancelada");
        alert.setContentText("la boleta externa alumnos se guardo correctamente");
        alert.showAndWait();

    }

    @FXML
    private void generarBoletaInterna(ActionEvent event) throws IOException {

        if (alumno.getCalificaciones().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se puede generar la boleta interna");
            alert.setContentText("El alumno aun no tiene calificaciones registradas");
            alert.showAndWait();
            return;
        }

        // Manipulando el fileChooser
        String workPath = System.getProperty("user.dir");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(workPath));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Acción cancelada");
            alert.setContentText("la boleta interna no se guardo");
            alert.showAndWait();
            return;
        }

        // capturamos la calificacion por mes
        List<CalificacionH> calificacionesBoleta = getCalificacionesActuales();

        BoletaInterna.generarPDF(file, calificacionesBoleta);

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Acción cancelada");
        alert.setContentText("la boleta interna se guardo correctamente");
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

    // IMPORTANTEE
    @FXML
    private void modificarCalifiaciones(ActionEvent event) {
        setCalificaciones(alumno);
    }

    @FXML
    private void seleccionarMes(ActionEvent event) {

        String mesNombre = cmbMes.getSelectionModel().getSelectedItem();
        btnModificarCalificacion.setDisable(true);

        mesSeleccionado = new MesH(mesNombre);
        mesSeleccionado = new MesDAOH().buscarPoNombre(mesSeleccionado);

        if (!esMesCalificado(alumno, mesNombre)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("No se puede modificar un mes que no haya sido capturado anteriormente");
            alert.showAndWait();
            return;
        }

        if (!esMesCalificado(alumno, mesNombre)) {
            limpiarTxtMaterias();
            return;
        }

        // llenar la informacion que anteriormente se guardo
        // para las materias academicas
        for (int i = 0; i < getCalificacionesAcademicasMensuales(mesNombre).size(); i++) {
            txtMateriasAcademicas.get(i)
                    .setText(getCalificacionesAcademicasMensuales(mesNombre).get(i).getResultado().toString());
            txtMateriasAcademicas.get(i).setEditable(true);
        }

        // para las materias complementarias
        for (int i = 0; i < getCalificacionesComplementariasMensuales(mesNombre).size(); i++) {
            txtMateriasComplementarias.get(i)
                    .setText(getCalificacionesComplementariasMensuales(mesNombre).get(i).getResultado().toString());
            txtMateriasComplementarias.get(i).setEditable(true);
        }

        // smell code :P
        List<InasistenciaH> inasistenciasMensual = alumno.getInasistencias().stream().filter(i -> {
            return i.getMes().getNombre().equals(mesNombre)
                    &&
                    i.getCicloEscolar().equals(cicloEscolar);
        })
                .collect(Collectors.toList());

        InasistenciaH numeroInasistencias = inasistenciasMensual.get(0);
        txtInasistencias.setText(numeroInasistencias.getCantidad().toString());

        btnModificarCalificacion.setDisable(false);
    }

    private void limpiarTxtMaterias() {
        txtMateriasAcademicas.forEach(txt -> {
            txt.clear();
        });

        txtMateriasComplementarias.forEach(txt -> {
            txt.clear();
        });
    }

    private List<CalificacionH> getCalificacionesAcademicasMensuales(String mesNombre) {
        return getCalificacionesMensuales(mesNombre).stream().filter(c -> {
            return c.getAsignatura().getTipoAsignatura().getNombre().equals("academica");
        }).collect(Collectors.toList());
    }

    private List<CalificacionH> getCalificacionesComplementariasMensuales(String mesNombre) {
        return getCalificacionesMensuales(mesNombre).stream().filter(c -> {
            return c.getAsignatura().getTipoAsignatura().getNombre().equals("complementaria");
        }).collect(Collectors.toList());
    }

    private List<CalificacionH> getCalificacionesMensuales(String mesNombre) {
        return alumno.getCalificaciones().stream().filter(c -> {
            return c.getGrado().getNombre().equals(alumno.getAula().getGrado().getNombre())
                    &&
                    c.getGrupo().getNombre().equals(alumno.getAula().getGrupo().getNombre())
                    &&
                    c.getMes().getNombre().equals(mesNombre);
        })
                .collect(Collectors.toList());
    }

    private List<CalificacionH> getCalificacionesActuales() {
        return alumno.getCalificaciones().stream().filter(c -> {
            return c.getGrado().getNombre().equals(alumno.getAula().getGrado().getNombre())
                    &&
                    c.getGrupo().getNombre().equals(alumno.getAula().getGrupo().getNombre());
        })
                .collect(Collectors.toList());
    }

    private boolean esMesCalificado(AlumnoH alumno, String mesNombre) {
        return getCalificacionesMensuales(mesNombre).size() == alumno.getAula().getGrado().getAsignaturas().size();
    }

    private void setCalificaciones(AlumnoH alumno) {

        List<AsignaturaH> listaMaterias = alumno.getAula().getGrado().getAsignaturas();
        String calificacionMateriaUi = "";
        Double calificacionMateria = 0.0;

        for (int i = 0; i < listaMaterias.size(); i++) {
            calificacionMateriaUi = txtMateriasGeneral.get(i).getText().trim();

            if (!calificacionMateriaUi.matches(regexCalificacion)) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Mensaje");
                alert.setContentText("Formato no valido para la calificacion");
                alert.showAndWait();
                return;
            }

            calificacionMateria = Double.parseDouble(calificacionMateriaUi);
            txtMateriasGeneral.get(i).setText(String.valueOf(Math.round(calificacionMateria)));

            if (calificacionMateria < 6) {
                txtMateriasGeneral.get(i).setText("6");
            }

            if (calificacionMateria > 10) {
                txtMateriasGeneral.get(i).setText("10");
            }
        }

        String inasistenciasUI = txtInasistencias.getText().trim();

        if (!inasistenciasUI.matches(regexInasistencias)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("FORMATO NO VALIDO PARA EL NUMERO DE INASISTENCIAS");
            alert.showAndWait();
            return;
        }

        Integer cantidadInasistencias = Integer.parseInt(inasistenciasUI);

        if(cantidadInasistencias > 5){ //este valor puede cambiar, aun tengo dudas
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText("EL NÚMERO DE INASISTENCIAS NO PUEDE SOBREPASAR MÁS DE 5");
            alert.showAndWait();
            
            txtInasistencias.setText("5");
            return;
        }

        // confirmacion de envio de datos
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de la modificación de calificaciones");
        alert.setContentText("¿Esta seguro que desea modificar las calificaciones?");
        Optional<ButtonType> btnRespuesta = alert.showAndWait();

        if (btnRespuesta.get() != ButtonType.OK) {
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Operación cancelada");
            alert.setContentText("Las calificaciones no se modificaron");
            alert.showAndWait();
            return;
        }

        // ENVIAMOS LOS DATOS A LA BD

        // empezamos con la modificacion de la calificacion
        List<CalificacionH> calificacionesMensual = getCalificacionesMensuales(mesSeleccionado.getNombre());

        for (int i = 0; i < listaMaterias.size(); i++) {

            calificacionMateriaUi = txtMateriasGeneral.get(i).getText().trim();
            calificacionMateria = Double.parseDouble(calificacionMateriaUi);

            CalificacionDAOH calificacionDAO = new CalificacionDAOH();
            CalificacionH calificacion = calificacionesMensual.get(i);
            calificacion.setResultado(calificacionMateria);
            calificacionDAO.actualizar(calificacion);

        }

        // agregar numero de inasistencias
        List<InasistenciaH> inasistencias = alumno.getInasistencias().stream().filter(i -> {
            return
            i.getMes().equals(mesSeleccionado)
            &&
            i.getCicloEscolar().equals(cicloEscolar);
        })
        .collect(Collectors.toList());

        InasistenciaH inasistenciaMensual = inasistencias.get(0);
        InasistenciaDAOH inasistenciaDAO = new InasistenciaDAOH();
        inasistenciaMensual.setCantidad(cantidadInasistencias);
        inasistenciaDAO.actualizar(inasistenciaMensual);
        
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setContentText("Calificaciones capturadas correctamente");
        alert.showAndWait();

    }

    private void llenarAsignaturasColumns(List<AsignaturaH> asignaturas) {

        setInvisibleLblMaterias();
        setInvisibleTxtMaterias();
        txtMateriasGeneral.clear();

        List<AsignaturaH> academicas = asignaturas.stream()
                .filter(a -> a.getTipoAsignatura().getNombre().equals("academica"))
                .collect(Collectors.toList());

        for (int i = 0; i < academicas.size(); i++) {
            lblMateriasAcademicas.get(i).setVisible(true);
            lblMateriasAcademicas.get(i).setText(academicas.get(i).getNombre());
        }

        for (int i = 0; i < academicas.size(); i++) {
            txtMateriasAcademicas.get(i).setVisible(true);
            txtMateriasGeneral.add(txtMateriasAcademicas.get(i));
        }

        List<AsignaturaH> complementarias = asignaturas.stream()
                .filter(a -> a.getTipoAsignatura().getNombre().equals("complementaria"))
                .collect(Collectors.toList());

        for (int i = 0; i < complementarias.size(); i++) {
            lblMateriasComplementarias.get(i).setVisible(true);
            lblMateriasComplementarias.get(i).setText(complementarias.get(i).getNombre());
        }

        for (int i = 0; i < complementarias.size(); i++) {
            txtMateriasComplementarias.get(i).setVisible(true);
            txtMateriasGeneral.add(txtMateriasComplementarias.get(i));
        }

    }

    public void iniciarSesion() {
        if (Sesion.nombreUsuario != null) {
            lblNombreUsuario.setText(Sesion.nombreUsuario);
        }
    }

    private void limpiarUI() {
        setInvisibleLblMaterias();
        setInvisibleTxtMaterias();
        txtGradoAlumno.clear();
        txtGrupoAlumno.clear();

        txtMateriasAcademicas.forEach(txt -> {
            txt.clear();
        });

        txtMateriasComplementarias.forEach(txt -> {
            txt.clear();
        });

    }

    private void setInvisibleLblMaterias() {
        lblMateriasAcademicas.forEach(lbl -> {
            lbl.setVisible(false);
        });

        lblMateriasComplementarias.forEach(lbl -> {
            lbl.setVisible(false);
        });
    }

    public void setInvisibleTxtMaterias() {
        txtMateriasAcademicas.forEach(txt -> {
            txt.setVisible(false);
        });

        txtMateriasComplementarias.forEach(txt -> {
            txt.setVisible(false);
        });
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lblMateriasAcademicas.add(lblAsignatura1);
        lblMateriasAcademicas.add(lblAsignatura2);
        lblMateriasAcademicas.add(lblAsignatura3);
        lblMateriasAcademicas.add(lblAsignatura4);
        lblMateriasAcademicas.add(lblAsignatura5);
        lblMateriasAcademicas.add(lblAsignatura6);
        lblMateriasAcademicas.add(lblAsignatura7);
        lblMateriasAcademicas.add(lblAsignatura8);
        lblMateriasAcademicas.add(lblAsignatura9);
        lblMateriasAcademicas.add(lblAsignatura10);

        lblMateriasComplementarias.add(lblAsignatura11);
        lblMateriasComplementarias.add(lblAsignatura12);
        lblMateriasComplementarias.add(lblAsignatura13);

        txtMateriasAcademicas.add(txtCalificacion1);
        txtMateriasAcademicas.add(txtCalificacion2);
        txtMateriasAcademicas.add(txtCalificacion3);
        txtMateriasAcademicas.add(txtCalificacion4);
        txtMateriasAcademicas.add(txtCalificacion5);
        txtMateriasAcademicas.add(txtCalificacion6);
        txtMateriasAcademicas.add(txtCalificacion7);
        txtMateriasAcademicas.add(txtCalificacion8);
        txtMateriasAcademicas.add(txtCalificacion9);
        txtMateriasAcademicas.add(txtCalificacion10);

        txtMateriasComplementarias.add(txtCalificacion11);
        txtMateriasComplementarias.add(txtCalificacion12);
        txtMateriasComplementarias.add(txtCalificacion13);

        setInvisibleLblMaterias();
        setInvisibleTxtMaterias();

        // escogiendo ciclo escolar
        cicloEscolar = new CicloEscolarDAOH().listar().get(0);

        new MesDAOH().listar().forEach(mes -> {
            cmbMes.getItems().add(mes.getNombre());
        });

        btnModificarCalificacion.setDisable(true);
        cmbMes.setDisable(true);
        btnBoletaInterna.setDisable(true);
        btnBoletaExterna.setDisable(true);
        txtInasistencias.setVisible(false);

    }

}
