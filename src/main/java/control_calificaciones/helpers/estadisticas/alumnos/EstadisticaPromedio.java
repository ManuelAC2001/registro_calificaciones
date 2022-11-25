package control_calificaciones.helpers.estadisticas.alumnos;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import control_calificaciones.data.*;
import control_calificaciones.models.*;
import lombok.*;

@ToString
public class EstadisticaPromedio {

    public EstadisticaPromedio(AulaH aula) {
        this.aula = aula;
    }

    public static List<AlumnoH> alumnos;
    public static List<MesH> meses = new MesDAOH().listar();

    private static Double promedioEscuela;

    @ToString.Exclude
    private AulaH aula;

    private String gradoNombre;
    private String grupoNombre;
    private String nombreAlumnoPromedioMaximo;

    private Double promedioMaximo;
    private Double promedioGeneral;

    public Double getPromedioGeneral() {

        List<AlumnoH> alumnos = aula.getAlumnos();

        Integer cantidadAlumnos = alumnos.size();

        Double sumatoriaPromedio = alumnos.stream()
                .map(a -> getPromedioAlumno(a))
                .mapToDouble(Double::valueOf)
                .sum();

        promedioGeneral =  sumatoriaPromedio / cantidadAlumnos;
        return promedioGeneral;
    }

    public static Double getPromedioEscuela() {
        
        alumnos = new AlumnoDAOH().listar();
        Integer cantidadAlumnos = alumnos.size();

        Double sumatoriaPromedio = alumnos.stream()
                .map(a -> getPromedioAlumno(a))
                .mapToDouble(Double::valueOf)
                .sum();

        EstadisticaPromedio.promedioEscuela = sumatoriaPromedio / cantidadAlumnos;
        return EstadisticaPromedio.promedioEscuela;
    }

    private AlumnoH getAlumnoPromedioMaximo() {

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

    public String getNombreAlumnoPromedioMaximo() {
        nombreAlumnoPromedioMaximo = getAlumnoPromedioMaximo().getNombreCompleto();
        return nombreAlumnoPromedioMaximo;
    }

    public String getGradoNombre(){
        gradoNombre = aula.getGrado().getNombre(); 
        return gradoNombre;
    }

    public String getGrupoNombre(){
        grupoNombre = aula.getGrupo().getNombre(); 
        return grupoNombre;
    }

    public Double getPromedioMaximo(){
        promedioMaximo = getPromedioAlumno(getAlumnoPromedioMaximo());
        return promedioMaximo;
    }

}
