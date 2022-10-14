package control_calificaciones;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;

import control_calificaciones.data.AlumnoDAO;
import control_calificaciones.data.AulaDAO;
import control_calificaciones.data.CorreoTutorDAO;
import control_calificaciones.data.TutorDAO;
import control_calificaciones.helpers.pdf.ListaPDF;
import control_calificaciones.models.Alumno;
import control_calificaciones.models.Aula;
import control_calificaciones.models.CorreoTutor;
import control_calificaciones.models.Tutor;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Test {

    public static void agregarAlumnoTutorTest() {

        /*-- Inicio del algoritmo de alta de alumnos --*/
        AlumnoDAO alumnoDAO = new AlumnoDAO();

        // creamos un objeto de alumno
        Alumno alumnoNuevo = new Alumno();
        alumnoNuevo.setCurp("AOCM011012HGRPSNA2");
        alumnoNuevo.setNombre("saul");
        alumnoNuevo.setApellido_paterno("apolinar");
        alumnoNuevo.setApellido_materno("castillo");
        alumnoNuevo.setFecha_nacimiento(Date.valueOf("2015-10-12"));
        alumnoNuevo.setGenero("H".charAt(0));


        // validaciones a nivel base de datos para la tabla alumnos
        if (alumnoDAO.esNombreRepetido(alumnoNuevo)) {
            System.out.println("No puedes ingresar alumnos con el mismo nombre completo");
            return;
        }

        if (alumnoDAO.esRepetido(alumnoNuevo)) {
            System.out.println("La curp ya esta asignada para otro alumno");
            return;
        }

        if (alumnoNuevo.getEdad() < 6) {
            System.out.println("El alumno es demasiado joven para ser ingresado");
            return;
        }

        if (alumnoNuevo.getEdad() > 12) {
            System.out.println("El alumno es demasiado grande para ser ingresado");
            return;
        }

        // Una vez que el alumno sea validado, tenemos creamos un tutor
        TutorDAO tutorDAO = new TutorDAO();
        String correo1 = "apol4@gmail.com";
        String correo2 = "apol5@gmail.com";
        // String correo2 = "apol2@gmail.com";

        
        if (correo1.equalsIgnoreCase(correo2)) {
            System.out.println("no puedes poner dos correos iguales");
            return;
        }

        Tutor tutor = new Tutor();
        tutor.setNombre("juan");
        tutor.setApellido_paterno("Gallegos");
        tutor.setApellido_materno("Basteri");

        // verficiar si el padre esta en el sistema
        if (tutorDAO.esRepetido(tutor)) {
            tutor = tutorDAO.buscarByNombreCompleto(tutor);
            alumnoNuevo.setId_tutor(tutor.getId_tutor());
        } else {

            // agregar correo o correos (maximo 2)
            if (!correo1.trim().isEmpty() && CorreoTutorDAO.esRepetido(correo1)) {
                System.out.println("El correo " + correo1 + " ya esta en uso");
                return;
            }

            if (!correo2.trim().isEmpty() && CorreoTutorDAO.esRepetido(correo2)) {
                System.out.println("El correo " + correo2 + " ya esta en uso");
                return;
            }

            tutorDAO.insertar(tutor);
            tutor = tutorDAO.buscarByNombreCompleto(tutor);

            if (!correo1.isEmpty()) {
                tutorDAO.insertarCorreo(tutor, correo1);
            }

            if (!correo2.isEmpty()) {
                tutorDAO.insertarCorreo(tutor, correo2);
            }

        }

        // guardar en aula
        String nombre_grado = "";
        AulaDAO aulaDAO = new AulaDAO();
        Aula aula = new Aula();
        ArrayList<Aula> aulas = new ArrayList<>();

        if (alumnoNuevo.getEdad() == 6) {
            nombre_grado = "primero";
            aulas = aulaDAO.getAulasByNombreGrado(nombre_grado);
        }

        if (alumnoNuevo.getEdad() == 7) {
            nombre_grado = "segundo";
            aulas = aulaDAO.getAulasByNombreGrado(nombre_grado);
        }

        if (alumnoNuevo.getEdad() == 8) {
            nombre_grado = "tercero";
            aulas = aulaDAO.getAulasByNombreGrado(nombre_grado);
        }

        if (alumnoNuevo.getEdad() == 9) {
            nombre_grado = "cuarto";
            aulas = aulaDAO.getAulasByNombreGrado(nombre_grado);
        }

        if (alumnoNuevo.getEdad() == 10) {
            nombre_grado = "quinto";
            aulas = aulaDAO.getAulasByNombreGrado(nombre_grado);
        }

        if (alumnoNuevo.getEdad() == 11 || alumnoNuevo.getEdad() == 12) {
            nombre_grado = "sexto";
            aulas = aulaDAO.getAulasByNombreGrado(nombre_grado);
        }

        // aulas.removeIf(aulaDisponible -> aulaDisponible.getCantidad() >= 25);
        aulas.removeIf(aulaDisponible -> aulaDisponible.getCantidad() >= 1);

        if (aulas.isEmpty()) {
            System.out.println("ya no hay cupos para este grado");
            return;
        }

        aula = aulas.get(0);
        System.out.println(aula);

        alumnoNuevo.setId_tutor(tutor.getId_tutor());
        alumnoNuevo.setId_aula(aula.getId_aula());

        alumnoDAO.insertar(alumnoNuevo);
    }

    public static void consultaAlumnoTest() {
        Alumno alumno = new Alumno();
        AlumnoDAO alumnoDAO = new AlumnoDAO();

        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutor = new Tutor();

        ArrayList<CorreoTutor> correosTutor = new ArrayList<>();

        alumno.setNombre("manuel");
        alumno.setApellido_paterno("apolinar");
        alumno.setApellido_materno("castillo");

        alumno = alumnoDAO.buscarByNombreCompleto(alumno);
        tutor = tutorDAO.buscarById(alumno.getId_tutor());
        correosTutor = tutorDAO.getCorreos(tutor);

        System.out.println(alumno);

        System.out.println("\ninfo del tutor del alumno");
        System.out.println(tutor);

        System.out.println("\ncorreo del tutor del alumno");
        correosTutor.forEach(correo -> {
            System.out.println(correo.getCorreo());
        });

    }

    public static void bajaAlumnoTest() {
        Alumno alumno = new Alumno();
        AlumnoDAO alumnoDAO = new AlumnoDAO();

        alumno.setNombre("piccolo");
        alumno.setApellido_paterno("apolinar");
        alumno.setApellido_materno("castillo");

        alumno = alumnoDAO.buscarByNombreCompleto(alumno);

        System.out.println("Alumno a eliminar");
        System.out.println(alumno);

        if (alumno == null) {
            System.out.println("No existe el alumno");
            return;
        }
        alumnoDAO.eliminar(alumno);
    }

    public static void modificarAlumnoTest() {
        Alumno alumno = new Alumno();
        AlumnoDAO alumnoDAO = new AlumnoDAO();

        Tutor tutor = new Tutor();
        TutorDAO tutorDAO = new TutorDAO();

        alumno.setNombre("Antonio");
        alumno.setApellido_paterno("santos");
        alumno.setApellido_materno("pacheco");

        alumno = alumnoDAO.buscarByNombreCompleto(alumno);

        if (alumno == null) {
            System.out.println("El alumno a modificar no existe");
            return;
        }

        tutor = tutorDAO.buscarById(alumno.getId_tutor());

        System.out.println("Alumno a modificar");
        System.out.println(alumno);

        System.out.println("\nTutor a modificar");
        System.out.println(tutor);

        //datos a modificar del alumno
        alumno.setNombre("Antonio");
        alumno.setApellido_paterno("Santos");
        alumno.setApellido_materno("pacheco");
        alumno.setGenero('H');

        if(alumnoDAO.esNombreRepetido(alumno)){

            if(!alumno.getCurp().equalsIgnoreCase(alumnoDAO.buscarByNombreCompleto(alumno).getCurp())){
                System.out.println("Ya existe un alumno con el mismo nombre");
                return;
            }

        }
        // datos a modificar del tutor
        tutor.setNombre("Antonio");
        tutor.setApellido_paterno("gallegos");
        tutor.setApellido_materno("basteri");

        // una vez modificado los datos, verifica que no coincida con un tutor anterior
        if (tutorDAO.esRepetido(tutor)) {

            if (tutor.getId_tutor() != tutorDAO.buscarByNombreCompleto(tutor).getId_tutor()) {
                System.out.println("ya existe un tutor con el mismo nombre");
                return;
            }
        }

        // tutor modificado
        tutorDAO.modificar(tutor);

        System.out.println("\nTutor modificado");
        System.out.println(tutor);
            
        System.out.println("\nAlumno modificado");
        alumnoDAO.modificar(alumno);
        System.out.println(alumno);
    }

    public static void main(String[] args) {

        // C
        // agregarAlumnoTutorTest();

        // R
        // consultaAlumnoTest();

        // U
        // modificarAlumnoTest();

        // D
        // bajaAlumnoTest();

    }

}
