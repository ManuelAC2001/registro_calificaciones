package control_calificaciones.models;

import javax.persistence.*;

import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "tipos_boleta")
public class TipoBoletaH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_boleta")
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Integer idTipoBoleta;
    
    private String nombre;

}
