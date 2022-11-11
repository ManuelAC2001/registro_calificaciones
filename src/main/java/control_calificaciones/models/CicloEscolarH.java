package control_calificaciones.models;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.*;

@Getter @Setter
@ToString @EqualsAndHashCode

@Entity
@Table(name = "ciclos_escolares")
public class CicloEscolarH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ciclo_escolar")
    @Setter(AccessLevel.PRIVATE)
    private Integer idCicloEscolar;

    @EqualsAndHashCode.Exclude
    private String nombre;
    
}
