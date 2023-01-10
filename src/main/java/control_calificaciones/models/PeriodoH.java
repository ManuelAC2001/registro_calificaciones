package control_calificaciones.models;

import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table( name = "periodos" )
public class PeriodoH {

    public PeriodoH() {}

    public PeriodoH(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public PeriodoH(String nombre) {
        this.nombre = nombre;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id_periodo" )
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Integer idPeriodo;
    
    private String nombre;

    @ToString.Exclude
    @OneToMany(mappedBy = "periodo")
    private List<MesH> meses;
    
}
