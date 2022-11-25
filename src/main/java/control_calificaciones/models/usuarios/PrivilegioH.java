package control_calificaciones.models.usuarios;

import java.util.List;

import javax.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "privilegios")
public class PrivilegioH {

    public PrivilegioH(){}

    public PrivilegioH(Integer idPrivilegio){
        this.idPrivilegio = idPrivilegio;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    @EqualsAndHashCode.Include
    @Column(name = "id_privilegio")
    private Integer idPrivilegio;
    
    private String nombre;

    @ToString.Exclude
    @ManyToMany(mappedBy = "privilegios")
    private List<RolH> roles;
}
