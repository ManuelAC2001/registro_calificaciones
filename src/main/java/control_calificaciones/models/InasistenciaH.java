package control_calificaciones.models;

import javax.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "inasistencias")
public class InasistenciaH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inasistencia")    
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Integer idInasistencia;
    
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "curp", referencedColumnName = "curp")
    private AlumnoH alumno;

    @ManyToOne
    @JoinColumn(name = "id_mes", referencedColumnName = "id_mes")
    private MesH mes;

    @ManyToOne
    @JoinColumn(name = "id_ciclo_escolar", referencedColumnName = "id_ciclo_escolar")
    private CicloEscolarH cicloEscolar;

}
