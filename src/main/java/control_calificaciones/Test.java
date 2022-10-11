package control_calificaciones;

import java.sql.Date;

import control_calificaciones.data.AlumnoDAO;
import control_calificaciones.data.CorreoTutorDAO;
import control_calificaciones.data.TutorDAO;
import control_calificaciones.models.Alumno;
import control_calificaciones.models.CorreoTutor;
import control_calificaciones.models.Tutor;

public class Test {

    public static void agregarAlumnoTutorTest() {

        /*-- Inicio del algoritmo de alta de alumnos --*/
        AlumnoDAO alumnoDAO = new AlumnoDAO();

        // creamos un objeto de alumno
        Alumno alumnoNuevo = new Alumno();
        alumnoNuevo.setCurp("AOCM011012HGRPSNA2");
        alumnoNuevo.setNombre("vegeta");
        alumnoNuevo.setApellido_paterno("apolinar");
        alumnoNuevo.setApellido_materno("castillo");
        alumnoNuevo.setFecha_nacimiento(Date.valueOf("2015-10-12"));
        alumnoNuevo.setGenero("H".charAt(0));

        alumnoNuevo.setId_aula(1);

        Alumno alumnoRepetido = alumnoDAO.buscarByNombreCompleto(alumnoNuevo);

        if (alumnoRepetido != null) {
            System.out.println("No puedes ingresar alumnos con el mismo nombre completo");
            return;
        }

        alumnoRepetido = alumnoDAO.buscar(alumnoNuevo.getCurp());

        if (alumnoRepetido != null) {
            System.out.println("La curp ya esta asignada para otro alumno");
            return;
        }

        if (alumnoNuevo.getEdad() > 12) {
            System.out.println("El chamaco ya esta demasiado grande para ser de primaria");
            return;
        }

        // Una vez que el alumno pueda sea validado sus datos, tenemos que asignarle un
        // tutor

        TutorDAO tutorDAO = new TutorDAO();
        String correo1 = "apol3@gmail.com";
        String correo2 = "apol3@gmail.com";

        // verficiar si el padre esta en el sistema
        Tutor tutor = new Tutor();
        tutor.setNombre("pedro");
        tutor.setApellido_paterno("Gallegos");
        tutor.setApellido_materno("Basteri");

        Tutor tutorEncontrado = tutorDAO.buscarByNombreCompleto(tutor);

        if(correo1.equalsIgnoreCase(correo2)){
            System.out.println("no puedes poner dos correos iguales");
            return;
        }

        if (tutorEncontrado != null) {
            // insertamos el id del tutor que ya existe
            alumnoNuevo.setId_tutor(tutorEncontrado.getId_tutor());
            alumnoDAO.insertar(alumnoNuevo);

            // agregar correo o correos (maximo 2)
            // falta implmentar mas logica aqui
            return;
        }

        // agregar correo o correos (maximo 2)

        CorreoTutor correoTutor = CorreoTutorDAO.buscar(correo1);
        if (correoTutor != null) {
            System.out.println("El correo " + correo1 + " ya esta en uso");
            return;
        }

        correoTutor = CorreoTutorDAO.buscar(correo2);

        if (correoTutor != null) {
            System.out.println("El correo " + correo2 + " ya esta en uso");
            return;
        }

        // el tutor no existe en el sistema, tenemos que crearlo
        tutorDAO.insertar(tutor);
        tutor = tutorDAO.buscarByNombreCompleto(tutor);

        alumnoNuevo.setId_tutor(tutor.getId_tutor());
        alumnoDAO.insertar(alumnoNuevo);

        tutorDAO.insertarCorreo(tutor, correo1);
        tutorDAO.insertarCorreo(tutor, correo2);

    }

    public static void main(String[] args) {

        agregarAlumnoTutorTest();

        // NOTAS
        // para eliminar al alumno solo basta con un delete alumno

    }

}
