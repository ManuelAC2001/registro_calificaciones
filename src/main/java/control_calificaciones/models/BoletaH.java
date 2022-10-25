package control_calificaciones.models;

import javax.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "boletas")
public class BoletaH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Integer folio;

    @Column(name = "ruta_pdf")
    private String rutaPdf;

    @ManyToOne
    @JoinColumn( name = "curp", referencedColumnName = "curp")
    private AlumnoH alumno;
    
    @ManyToOne
    @JoinColumn(name = "id_tipo_boleta", referencedColumnName = "id_tipo_boleta")
    private TipoBoletaH tipoBoleta;

}
