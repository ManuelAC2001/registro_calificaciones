package control_calificaciones;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import control_calificaciones.data.AlumnoDAOH;
import control_calificaciones.data.AulaDAOH;
import control_calificaciones.data.GradoDAOH;
import control_calificaciones.data.MesDAOH;
import control_calificaciones.models.AlumnoH;
import control_calificaciones.models.AulaH;
import control_calificaciones.models.CalificacionH;
import control_calificaciones.models.GradoH;
import control_calificaciones.models.MesH;

public class Test {

    static List<AlumnoH> alumnos = new AlumnoDAOH().listar();
    static List<GradoH> grados = new GradoDAOH().listar();
    static List<MesH> meses = new MesDAOH().listar();

    static List<AulaH> aulas = new AulaDAOH()
            .listar()
            .stream()
            .filter(aula -> !aula.getAlumnos().isEmpty())
            .collect(Collectors.toList());

    public static void main(String[] args) {

        // // Numero total de alumnos
        // System.out.println("Número total de alumnos" + alumnos.size());

        // // Numero total de alumnos por cada grado
        // grados.forEach(grado -> {
        // getNumeroAlumnosByGrado(grado);
        // });

        // // Calificacion maxima por total de alumnos
        // getMaxCalificacion();

        // //calificaciones maximas por aula
        // aulas.forEach(aula -> {
        // getMaxCalificacionByAula(aula);
        // });

        // //Cantidad de alumnos total
        // //cantidad de niños y niñas total
        // getNumeroAlumnosTotal();

        // // Cantidad de alumnos total por salon y
        // // cantidad de niños y niñas por salon
        // aulas.forEach(aula -> {
        // getNumeroAlumnosByAula(aula);
        // });

    }

    private static void getMaxCalificacionByAula(AulaH aula) {

        List<AlumnoH> allumnosByAula = alumnos
                .stream()
                .filter(a -> a.getAula().equals(aula))
                .collect(Collectors.toList());

        List<Double> promedios = allumnosByAula
                .stream()
                .map(a -> getPromedioAlumno(a))
                .collect(Collectors.toList());

        Double calificacionMaxima = Collections.max(promedios);

        List<AlumnoH> alumnosMaxPromedio = allumnosByAula
                .stream()
                .filter(a -> getPromedioAlumno(a) >= calificacionMaxima)
                .collect(Collectors.toList());

        System.out.println("Alumnos con mejor promedio del aula " + aula.getNombreAula());
        alumnosMaxPromedio.forEach(alumno -> {
            System.out.println(alumno.getNombreCompleto());
            System.out.println(getPromedioAlumno(alumno));
        });
        System.out.println("--------------------------------");

    }

    private static void getMaxCalificacion() {

        List<Double> promedios = alumnos.stream().map(a -> getPromedioAlumno(a)).collect(Collectors.toList());
        Double calificacionMaxima = Collections.max(promedios);

        List<AlumnoH> alumnosMaxPromedio = alumnos.stream().filter(a -> getPromedioAlumno(a) >= calificacionMaxima)
                .collect(Collectors.toList());

        System.out.println("Alumnos con mejor promedio");
        alumnosMaxPromedio.forEach(alumno -> {
            System.out.println(alumno.getNombreCompleto());
            System.out.println(getPromedioAlumno(alumno));
        });

    }

    private static Double getPromedioAlumno(AlumnoH alumno) {

        List<CalificacionH> calificaciones = alumno.getCalificaciones()
                .stream()
                .filter(c -> !c.getMes().getNombre().equals("diagnostico"))
                .collect(Collectors.toList());

        List<MesH> mesesParaEvaluar = meses.stream().filter(m -> !m.getNombre().equals("diagnostico"))
                .collect(Collectors.toList());

        List<MesH> mesesCalificados = calificaciones
                .stream()
                .map(a -> a.getMes())
                .distinct()
                .collect(Collectors.toList());

        Integer cantidadMesesCalificados = mesesCalificados.size();
        Integer cantidadMeses = mesesParaEvaluar.size();

        if (cantidadMesesCalificados != cantidadMeses) {
            return 0.0;
        }

        Double sumaCalificaciones = calificaciones
                .stream()
                .map(c -> c.getResultado())
                .mapToDouble(Double::valueOf)
                .sum();

        Double promedio = sumaCalificaciones / calificaciones.size();

        return Double.parseDouble(String.format("%.2f", promedio));

    }

    private static void getNumeroAlumnosTotal() {

        List<AlumnoH> alumnosMujeres = Test.alumnos
                .stream()
                .filter(a -> a.getGenero().equals('M'))
                .collect(Collectors.toList());

        List<AlumnoH> alumnosHombres = Test.alumnos
                .stream()
                .filter(a -> a.getGenero().equals('H'))
                .collect(Collectors.toList());

        System.out.println("Número de alumnos: " + Test.alumnos.size());
        System.out.println("Número de alumnos mujeres: " + alumnosMujeres.size());
        System.out.println("Número de alumnos hombres: " + alumnosHombres.size());

    }

    private static void getNumeroAlumnosByAula(AulaH aula) {

        System.out.println("\n--------------------------------");
        System.out.println("Aula: " + aula.getGrado().getNombre() + " " +
                aula.getGrupo().getNombre());

        List<AlumnoH> alumnos = aula.getAlumnos();

        List<AlumnoH> alumnosMujeres = aula.getAlumnos()
                .stream()
                .filter(a -> a.getGenero().equals('M'))
                .collect(Collectors.toList());

        List<AlumnoH> alumnosHombres = aula.getAlumnos()
                .stream()
                .filter(a -> a.getGenero().equals('H'))
                .collect(Collectors.toList());

        System.out.println("Número de alumnos: " + alumnos.size());
        System.out.println("Número de alumnos mujeres: " + alumnosMujeres.size());
        System.out.println("Número de alumnos hombres: " + alumnosHombres.size());

    }

    private static void getNumeroAlumnosByGrado(GradoH grado) {

        List<AlumnoH> alumnosGrado = alumnos.stream()
                .filter(alumno -> alumno.getAula().getGrado().equals(grado))
                .collect(Collectors.toList());

        String mensaje = "Cantidad de alumnos de " + grado.getNombre() + " grado: " + alumnosGrado.size();

        System.out.println(mensaje);

    }

}