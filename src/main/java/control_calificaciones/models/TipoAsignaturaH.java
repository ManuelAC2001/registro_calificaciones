package control_calificaciones.models;

import javax.persistence.*;

import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "tipo_asignaturas")
public class TipoAsignaturaH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_asignatura")
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Integer idTipoAsignatura;
    
    private String nombre;

}
