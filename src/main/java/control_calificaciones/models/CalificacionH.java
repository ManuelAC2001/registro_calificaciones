package control_calificaciones.models;

import javax.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "calificaciones")
public class CalificacionH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion")
    @Setter(AccessLevel.PRIVATE)
    @EqualsAndHashCode.Include
    private Integer idCalificacion;

    private Double resultado;    

    @ManyToOne
    @JoinColumn(name = "curp", referencedColumnName = "curp")
    private AlumnoH alumno;

    @ManyToOne
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo")
    private GrupoH grupo;

    @ManyToOne
    @JoinColumn(name = "id_grado", referencedColumnName = "id_grado")
    private GradoH grado;

    @ManyToOne
    @JoinColumn(name = "id_asignatura", referencedColumnName = "id_asignatura")
    private AsignaturaH asignatura;
    
    @ManyToOne
    @JoinColumn(name = "id_mes", referencedColumnName = "id_mes")
    private MesH mes;

    @ManyToOne
    @JoinColumn(name = "id_ciclo_escolar", referencedColumnName = "id_ciclo_escolar")
    private CicloEscolarH cicloEscolar;

}
