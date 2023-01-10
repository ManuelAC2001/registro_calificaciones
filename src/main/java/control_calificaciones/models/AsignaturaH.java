package control_calificaciones.models;

import javax.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "asignaturas")
public class AsignaturaH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignatura")
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Integer idAsignatura;

    @Getter @Setter
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_tipo_asignatura", referencedColumnName = "id_tipo_asignatura")
    @Getter @Setter
    private TipoAsignaturaH tipoAsignatura;

    @ManyToOne
    @JoinColumn(name = "id_grado", referencedColumnName = "id_grado")
    @Getter @Setter
    private GradoH grado;
    
}
