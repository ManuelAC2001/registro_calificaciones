package control_calificaciones.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import control_calificaciones.App;
import control_calificaciones.data.AlumnoDAOH;
import control_calificaciones.data.CalificacionDAOH;
import control_calificaciones.data.CicloEscolarDAOH;
import control_calificaciones.data.MesDAOH;
import control_calificaciones.data.usuarios.UsuarioDAO;
import control_calificaciones.models.AlumnoH;
import control_calificaciones.models.AsignaturaH;
import control_calificaciones.models.CalificacionH;
import control_calificaciones.models.CicloEscolarH;
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
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CapturaCalificacionesController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button btnRegistrarCalificacion;

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

    private List<Label> lblMateriasAcademicas = new ArrayList<>();
    private List<Label> lblMateriasComplementarias = new ArrayList<>();

    private List<TextField> txtMateriasAcademicas = new ArrayList<>();
    private List<TextField> txtMateriasComplementarias = new ArrayList<>();

    private CicloEscolarH cicloEscolar;
    private MesH mesSeleccionado;
    private AlumnoH alumno;

    @FXML
    private ComboBox<String> cmbMes;

    @FXML
    private void buscar(ActionEvent event) {

        AlumnoDAOH alumnoDAO = new AlumnoDAOH();
        alumno = new AlumnoH();
        alumno.setNombre(txtNombreAlumno.getText().trim());
        alumno.setApellidoPaterno(txtApellidoPatAlumno.getText().trim());
        alumno.setApellidoMaterno(txtApellidoMatAlumno.getText().trim());

        alumno = alumnoDAO.buscarNombre(alumno);

        if (alumno == null) {
            limpiarUI();
            return;
        }

        // asignamos grado y grupo
        txtGrupoAlumno.setText(alumno.getAula().getGrupo().getNombre());
        txtGradoAlumno.setText(alumno.getAula().getGrado().getNombre());

        // creamos la interfaz grafica de llenado de calificacioenes por materia
        llenarAsignaturasColumns(alumno.getAula().getGrado().getAsignaturas());

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

    @FXML
    private void registrarCalifiaciones(ActionEvent event) {
        setCalifiacionesAcademicas(alumno);
        setCalifiacionesComplementarias(alumno);
    }

    @FXML
    private void seleccionarMes(ActionEvent event) {
        
        String mesNombre = cmbMes.getSelectionModel().getSelectedItem();
        
        if (mesNombre.equals("octubre") && !esMesCalificado(alumno, "septiembre")) {
            System.out.println("NOOOO");
            return;
        }

        if (mesNombre.equals("noviembre/diciembre") && !esMesCalificado(alumno, "octubre")) {
            System.out.println("NOOOO");
            return;
        }


        if (esMesCalificado(alumno, mesNombre)) {

            //desabilitamos el boton de registrar
            btnRegistrarCalificacion.setDisable(true);    

            // llenar la informacion que anteriormente se guardo
            //para las materias academicas
            for (int i = 0; i < getCalificacionesAcademicasMensuales(mesNombre).size(); i++) {
                txtMateriasAcademicas.get(i)
                .setText(getCalificacionesAcademicasMensuales(mesNombre).get(i).getResultado().toString());
                txtMateriasAcademicas.get(i).setEditable(false);
            }
            
            //para las materias complementarias
            for (int i = 0; i < getCalificacionesComplementariasMensuales(mesNombre).size(); i++) {
                txtMateriasComplementarias.get(i)
                .setText(getCalificacionesAcademicasMensuales(mesNombre).get(i).getResultado().toString());
                txtMateriasComplementarias.get(i).setEditable(false);
            }
            return;
        }



        btnRegistrarCalificacion.setDisable(false);
        limpiarTxtMaterias();

        //habilitamos de nuevo los txt de las materias
        txtMateriasAcademicas.forEach(txt -> {
            txt.setEditable(true);
        });

        txtMateriasComplementarias.forEach(txt -> {
            txt.setEditable(true);
        });


        mesSeleccionado = new MesH(mesNombre);
        mesSeleccionado = new MesDAOH().buscarPoNombre(mesSeleccionado);
    }

    private void limpiarTxtMaterias(){
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

    private boolean esMesCalificado(AlumnoH alumno, String mesNombre) {
        return getCalificacionesMensuales(mesNombre).size() == alumno.getAula().getGrado().getAsignaturas().size();
    }

    private void setCalifiacionesAcademicas(AlumnoH alumno) {
        List<AsignaturaH> listAcademicas = alumno.getAula().getGrado().getAsignaturas().stream()
                .filter(a -> a.getTipoAsignatura().getNombre().equals("academica"))
                .collect(Collectors.toList());

        for (int i = 0; i < listAcademicas.size(); i++) {
            CalificacionDAOH calificacionDAO = new CalificacionDAOH();
            Integer calificacionMateria = Integer.parseInt(txtMateriasAcademicas.get(i).getText());

            CalificacionH calificacion = new CalificacionH();
            calificacion.setAlumno(alumno);
            calificacion.setGrupo(alumno.getAula().getGrupo());
            calificacion.setGrado(alumno.getAula().getGrado());
            calificacion.setAsignatura(listAcademicas.get(i));
            calificacion.setCicloEscolar(cicloEscolar);
            calificacion.setMes(mesSeleccionado);
            calificacion.setResultado(calificacionMateria);

            calificacionDAO.insertar(calificacion);

            //agregando las calificaciones recien agregadas
            alumno.getCalificaciones().add(calificacion);
        }
        
    }

    private void setCalifiacionesComplementarias(AlumnoH alumno) {

        CalificacionDAOH calificacionDAO = new CalificacionDAOH();

        List<AsignaturaH> listComplementarias = alumno.getAula().getGrado().getAsignaturas().stream()
                .filter(a -> a.getTipoAsignatura().getNombre().equals("complementaria"))
                .collect(Collectors.toList());

        for (int i = 0; i < listComplementarias.size(); i++) {
            Integer calificacionMateria = Integer.parseInt(txtMateriasComplementarias.get(i).getText());

            CalificacionH calificacion = new CalificacionH();
            calificacion.setAlumno(alumno);
            calificacion.setGrupo(alumno.getAula().getGrupo());
            calificacion.setGrado(alumno.getAula().getGrado());
            calificacion.setAsignatura(listComplementarias.get(i));
            calificacion.setCicloEscolar(cicloEscolar);
            calificacion.setMes(mesSeleccionado);
            calificacion.setResultado(calificacionMateria);

            calificacionDAO.insertar(calificacion);

            //agregando las calificaciones recien agregadas
            alumno.getCalificaciones().add(calificacion);
        }
    }

    private void llenarAsignaturasColumns(List<AsignaturaH> asignaturas) {

        setInvisibleLblMaterias();
        setInvisibleTxtMaterias();

        List<AsignaturaH> academicas = asignaturas.stream()
                .filter(a -> a.getTipoAsignatura().getNombre().equals("academica"))
                .collect(Collectors.toList());

        for (int i = 0; i < academicas.size(); i++) {
            lblMateriasAcademicas.get(i).setVisible(true);
            lblMateriasAcademicas.get(i).setText(academicas.get(i).getNombre());
        }

        for (int i = 0; i < academicas.size(); i++) {
            txtMateriasAcademicas.get(i).setVisible(true);
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
        }

    }

    public void iniciarSesion() {
        // lblNombreUsuario.setText(Sesion.nombreUsuario);
    }

    private void limpiarUI() {
        txtGradoAlumno.clear();
        txtGrupoAlumno.clear();
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

        btnRegistrarCalificacion.setDisable(true);

    }

}