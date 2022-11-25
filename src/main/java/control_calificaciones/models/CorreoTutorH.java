package control_calificaciones.models;

import javax.persistence.*;

import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "correos_tutores")
public class CorreoTutorH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_correo")
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Integer idCorreo;
    
    private String correo;

    @ManyToOne
    @JoinColumn(name = "id_tutor", referencedColumnName = "id_tutor")
    private TutorH tutor;
}
