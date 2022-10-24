package control_calificaciones.models;

import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "tutores")
public class TutorH {

    public TutorH() {}

    public TutorH(Integer idTutor) {
        this.idTutor = idTutor;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tutor")
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Integer idTutor;
    
    private String nombre;
    
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    
    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @OneToMany(mappedBy = "tutor")
    @ToString.Exclude
    private List<CorreoTutorH> correos;
    
    @OneToMany(mappedBy = "tutor")
    @ToString.Exclude
    private List<AlumnoH> hijos;

    public String getNombreCompleto(){
        return 
            this.apellidoPaterno + " " +
            this.apellidoMaterno + " " +
            this.nombre;
    }
}
