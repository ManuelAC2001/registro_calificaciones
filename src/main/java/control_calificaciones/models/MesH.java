package control_calificaciones.models;

import javax.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table( name = "meses" )
public class MesH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mes")
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Integer idMes;
    
    private String nombre;

    @ManyToOne
    @JoinColumn( name = "id_periodo", referencedColumnName = "id_periodo" )
    private PeriodoH periodo;

}
