package control_calificaciones;

import java.util.List;
import java.util.stream.Collectors;

import control_calificaciones.data.*;
import control_calificaciones.models.*;

public class TestEstadisticasBasicas {

    static List<AulaH> aulas = new AulaDAOH()
            .listar()
            .stream()
            .filter(aula -> !aula.getAlumnos().isEmpty())
            .collect(Collectors.toList());

    public static void main(String[] args) {

        aulas.forEach(a -> {
            System.out.println("Aula: " + a.getNombreAula());
            System.out.println("Numero de alumnos: " + a.getAlumnos().size());
            System.out.println("Numero de alumnos mujeres: " + getNumeroMujeresByAula(a));
            System.out.println("Numero de alumnos hombres: " + getNumeroHombresByAula(a));
            System.out.println("----------------------------------------------------------------\n");
        });

    }

    private static Integer getNumeroMujeresByAula(AulaH aula) {

        List<AlumnoH> alumnos = aula.getAlumnos();

        List<AlumnoH> mujeres = alumnos
                .stream()
                .filter(a -> a.getGenero().equals('M'))
                .collect(Collectors.toList());

        return mujeres.size();

    }

    private static Integer getNumeroHombresByAula(AulaH aula) {

        List<AlumnoH> alumnos = aula.getAlumnos();

        List<AlumnoH> hombres = alumnos
                .stream()
                .filter(a -> a.getGenero().equals('H'))
                .collect(Collectors.toList());

        return hombres.size();

    }

    // private static void getNumeroAlumnosByAula(AulaH aula) {

    // System.out.println("\n--------------------------------");
    // System.out.println("Aula: " + aula.getGrado().getNombre() + " " +
    // aula.getGrupo().getNombre());

    // List<AlumnoH> alumnos = aula.getAlumnos();

    // List<AlumnoH> alumnosMujeres = aula.getAlumnos()
    // .stream()
    // .filter(a -> a.getGenero().equals('M'))
    // .collect(Collectors.toList());

    // List<AlumnoH> alumnosHombres = aula.getAlumnos()
    // .stream()
    // .filter(a -> a.getGenero().equals('H'))
    // .collect(Collectors.toList());

    // System.out.println("Número de alumnos: " + alumnos.size());
    // System.out.println("Número de alumnos mujeres: " + alumnosMujeres.size());
    // System.out.println("Número de alumnos hombres: " + alumnosHombres.size());

    // }

}
