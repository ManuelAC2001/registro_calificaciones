package control_calificaciones;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import control_calificaciones.data.*;
import control_calificaciones.helpers.estadisticas.alumnos.EstadisticaPromedio;
import control_calificaciones.models.*;

public class TestEstadisticaPromedio {

    static List<AlumnoH> alumnos = new AlumnoDAOH().listar();
    static List<MesH> meses = new MesDAOH().listar();


    static List<AulaH> aulas = new AulaDAOH()
            .listar()
            .stream()
            .filter(aula -> !aula.getAlumnos().isEmpty())
            .collect(Collectors.toList());

    public static void main(String[] args) {

        aulas.forEach(a -> {
            EstadisticaPromedio e = new EstadisticaPromedio(a);
            System.out.println(e);
            System.out.println("--------------------------------");
        });


    }

    private static Double getPromedioGeneralByAula(AulaH aula) {

        List<AlumnoH> alumnos = aula.getAlumnos();

        Integer cantidadAlumnos = alumnos.size();

        Double sumatoriaPromedio = alumnos.stream()
                .map(a -> getPromedioAlumno(a))
                .mapToDouble(Double::valueOf)
                .sum();

        return sumatoriaPromedio / cantidadAlumnos;
    }

    private static Double getPromedioGeneral() {

        Integer cantidadAlumnos = alumnos.size();

        Double sumatoriaPromedio = alumnos.stream()
                .map(a -> getPromedioAlumno(a))
                .mapToDouble(Double::valueOf)
                .sum();

        return sumatoriaPromedio / cantidadAlumnos;
    }

    private static AlumnoH getAlumnoMaxPromedioByAula(AulaH aula) {

        List<AlumnoH> allumnosByAula = alumnos
                .stream()
                .filter(a -> a.getAula().equals(aula))
                .collect(Collectors.toList());

        List<Double> promedios = allumnosByAula
                .stream()
                .map(a -> getPromedioAlumno(a))
                .collect(Collectors.toList());

        Double calificacionMaxima = Collections.max(promedios);

        AlumnoH alumnoMaxPromedio = allumnosByAula
                .stream()
                .filter(a -> getPromedioAlumno(a) >= calificacionMaxima)
                .collect(Collectors.toList())
                .get(0);

        return alumnoMaxPromedio;
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

}
