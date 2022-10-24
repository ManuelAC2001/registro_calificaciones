package control_calificaciones.models;

import javax.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "aulas")
public class AulaH {

    public AulaH(){}

    public AulaH(Integer idAula){
        this.idAula = idAula;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula")
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Integer idAula;

    @ManyToOne
    @JoinColumn(name = "id_grado", referencedColumnName = "id_grado")
    private GradoH grado;

    @ManyToOne
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo")
    private GrupoH grupo;
}
