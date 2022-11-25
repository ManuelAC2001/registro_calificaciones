package control_calificaciones.models;

import javax.persistence.*;
import lombok.*;

@ToString
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table( name = "grupos" )
public class GrupoH {
    public GrupoH() {}

    public GrupoH(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Integer idGrupo;
    
    private String nombre;
}
