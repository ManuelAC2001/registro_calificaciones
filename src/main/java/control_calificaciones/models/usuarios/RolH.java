package control_calificaciones.models.usuarios;

import java.util.List;

import javax.persistence.*;
import lombok.*;

@Getter 
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "roles")
public class RolH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer id_rol;

    @Setter
    private String nombre;

    @ManyToMany( cascade = {CascadeType.ALL} )
    @JoinTable(
        name = "roles_privilegios",
        joinColumns = { @JoinColumn( name = "id_rol" ) },
        inverseJoinColumns = { @JoinColumn( name = "id_privilegio" ) }
    )
    private List<PrivilegioH> privilegios;

}
