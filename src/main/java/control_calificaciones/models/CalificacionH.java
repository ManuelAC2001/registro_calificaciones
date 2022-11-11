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

    @ManyToOne(cascade =  CascadeType.PERSIST)
    @JoinColumn(name = "curp", referencedColumnName = "curp")
    private AlumnoH alumno;

    @ManyToOne(cascade =  CascadeType.PERSIST)
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo")
    private GrupoH grupo;

    @ManyToOne(cascade =  CascadeType.PERSIST)
    @JoinColumn(name = "id_grado", referencedColumnName = "id_grado")
    private GradoH grado;

    @ManyToOne(cascade =  CascadeType.PERSIST)
    @JoinColumn(name = "id_asignatura", referencedColumnName = "id_asignatura")
    private AsignaturaH asignatura;
    
    @ManyToOne(cascade =  CascadeType.PERSIST)
    @JoinColumn(name = "id_mes", referencedColumnName = "id_mes")
    private MesH mes;

    @ManyToOne(cascade =  CascadeType.PERSIST)
    @JoinColumn(name = "id_ciclo_escolar", referencedColumnName = "id_ciclo_escolar")
    private CicloEscolarH cicloEscolar;

}
