package control_calificaciones.models.usuarios;

import javax.persistence.*;

import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "usuarios")
public class UsuarioH {

    public UsuarioH() {}

    public UsuarioH(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    private String correo;
    
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    
    private String contrasenia;
    
    private String codigo;

    @ManyToOne(cascade =  CascadeType.PERSIST)
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    private RolH rol;
    
}
