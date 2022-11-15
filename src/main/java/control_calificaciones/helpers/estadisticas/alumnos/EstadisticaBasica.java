package control_calificaciones.helpers.estadisticas.alumnos;

import java.util.List;
import java.util.stream.Collectors;

import control_calificaciones.data.AlumnoDAOH;
import control_calificaciones.models.AlumnoH;
import control_calificaciones.models.AulaH;
import control_calificaciones.models.GradoH;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EstadisticaBasica {

    public EstadisticaBasica(AulaH aula) {

        this.aula = aula;
        this.gradoNombre = aula.getGrado().getNombre();
        this.grupoNombre = aula.getGrupo().getNombre();
        this.cantidadAlumnosTotal = aula.getAlumnos().size();
        this.cantidadHombres = getCantidadHombresAlumnos();
        this.cantidadMujeres = getCantidadMujeresAlumnos();

        
    }

    public EstadisticaBasica(GradoH grado) {

        this.gradoNombre = grado.getNombre();
        this.cantidadAlumnosGrado = getCatidadAlumnosByGrado(grado);
        
    }


    
    private static List<AlumnoH> alumnos = new AlumnoDAOH().listar();

    private String gradoNombre;
    private String grupoNombre;
    private Integer cantidadAlumnosTotal;
    private Integer cantidadMujeres;
    private Integer cantidadHombres;
    
    private Integer cantidadAlumnosGrado;

    @ToString.Exclude
    private AulaH aula;

    private Integer getCantidadMujeresAlumnos() {

        List<AlumnoH> alumnos = aula.getAlumnos();

        List<AlumnoH> mujeres = alumnos
                .stream()
                .filter(a -> a.getGenero().equals('M'))
                .collect(Collectors.toList());

        return mujeres.size();

    }

    private Integer getCantidadHombresAlumnos() {

        List<AlumnoH> alumnos = aula.getAlumnos();

        List<AlumnoH> hombres = alumnos
                .stream()
                .filter(a -> a.getGenero().equals('H'))
                .collect(Collectors.toList());

        return hombres.size();

    }

    public Integer getCatidadAlumnosByGrado(GradoH grado) { 

        List<AlumnoH> alumnosGrado = alumnos.stream()
                .filter(alumno -> alumno.getAula().getGrado().equals(grado))
                .collect(Collectors.toList());

        return alumnosGrado.size();
    }

}
