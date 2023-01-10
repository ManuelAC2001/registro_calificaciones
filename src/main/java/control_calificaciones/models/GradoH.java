package control_calificaciones.models;

import java.util.List;

import javax.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "grados")
public class GradoH {
    
    public GradoH(){}

    public GradoH(Integer idGrado) {
        this.idGrado = idGrado;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grado")
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Integer idGrado;

    private String nombre;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "grado")
    private List<AsignaturaH> asignaturas;

}
