package control_calificaciones.models;

import javax.persistence.*;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "bitacora_sesion_usuarios")
public class BitacoraSesionH {

    public BitacoraSesionH() {}

    public BitacoraSesionH(Integer idBitacoraUsuario) {
        this.idBitacoraUsuario = idBitacoraUsuario;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "hhhh ")
    private Integer idBitacoraUsuario;
    
    private String usuario;
    
    private Integer dia;
    
    private Integer anio;
    
    private String mes;
    
    @Column(name = "hora_entrada")
    private String horaEntrada;
    
    @Column(name = "hora_salida")
    private String horaSalida;
}
