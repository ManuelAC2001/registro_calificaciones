package control_calificaciones.models;

import java.util.List;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "aulas")
public class AulaH {

    public AulaH() {
    }

    public AulaH(Integer idAula) {
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

    @OneToMany(mappedBy = "aula")
    @ToString.Exclude
    private List<AlumnoH> alumnos;

    public String getNombreAula() {
        return this.getGrado().getNombre() + " " + this.getGrupo().getNombre();
    }

}
