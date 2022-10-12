package control_calificaciones;

import java.sql.Date;
import java.util.ArrayList;

import control_calificaciones.data.AlumnoDAO;
import control_calificaciones.data.AulaDAO;
import control_calificaciones.data.CorreoTutorDAO;
import control_calificaciones.data.TutorDAO;
import control_calificaciones.models.Alumno;
import control_calificaciones.models.Aula;
import control_calificaciones.models.Tutor;

public class Test {

    public static void agregarAlumnoTutorTest() {

        /*-- Inicio del algoritmo de alta de alumnos --*/
        AlumnoDAO alumnoDAO = new AlumnoDAO();

        // creamos un objeto de alumno
        Alumno alumnoNuevo = new Alumno();
        alumnoNuevo.setCurp("AOCM011012HGRPSNA1");
        alumnoNuevo.setNombre("mike");
        alumnoNuevo.setApellido_paterno("apolinar");
        alumnoNuevo.setApellido_materno("castillo");
        alumnoNuevo.setFecha_nacimiento(Date.valueOf("2015-10-12"));
        alumnoNuevo.setGenero("H".charAt(0));

        // alumnoNuevo.setId_aula(1);

        // validaciones a nivel base de datos para la tabla alumnos
        if (alumnoDAO.esNombreRepetido(alumnoNuevo)) {
            System.out.println("No puedes ingresar alumnos con el mismo nombre completo");
            return;
        }

        if (alumnoDAO.esRepetido(alumnoNuevo)) {
            System.out.println("La curp ya esta asignada para otro alumno");
            return;
        }

        if(alumnoNuevo.getEdad() < 6){
            System.out.println("El alumno es demasiado joven para ser ingresado");
            return;
        }

        if(alumnoNuevo.getEdad() > 12){
            System.out.println("El alumno es demasiado grande para ser ingresado");
            return;
        }

        // Una vez que el alumno sea validado, tenemos creamos un tutor
        TutorDAO tutorDAO = new TutorDAO();
        String correo1 = "apol3@gmail.com";
        String correo2 = "";
        // String correo2 = "apol2@gmail.com";

        Tutor tutor = new Tutor();
        tutor.setNombre("juan");
        tutor.setApellido_paterno("Gallegos");
        tutor.setApellido_materno("Basteri");

        if (correo1.equalsIgnoreCase(correo2)) {
            System.out.println("no puedes poner dos correos iguales");
            return;
        }

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

            // asignacion de aula (codigo en mejora)
            tutorDAO.insertar(tutor);
            tutor = tutorDAO.buscarByNombreCompleto(tutor);

            if(!correo1.isEmpty()){
                tutorDAO.insertarCorreo(tutor, correo1);
            }

            if(!correo2.isEmpty()){
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

        aulas.removeIf(aulaDisponible -> aulaDisponible.getCantidad() >= 1);

        if(aulas.isEmpty()){
            System.out.println("ya no hay cupos para este grado");
            return;
        }

        aula = aulas.get(0);
        System.out.println(aula);

        alumnoNuevo.setId_tutor(tutor.getId_tutor());
        alumnoNuevo.setId_aula(aula.getId_aula());

        alumnoDAO.insertar(alumnoNuevo);
    }



    public static void main(String[] args) {

        agregarAlumnoTutorTest();


    }

}
