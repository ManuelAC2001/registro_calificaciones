package control_calificaciones.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "alumnos")
public class AlumnoH {

    public AlumnoH() {}

    public AlumnoH(String curp) {
        this.curp = curp;
    }

    @Id
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private String curp;
    
    private String nombre;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    private Character genero;

    @Column(name =  "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "id_tutor", referencedColumnName = "id_tutor") 
    private TutorH tutor; 

    @ManyToOne
    @JoinColumn(name = "id_aula", referencedColumnName = "id_aula")
    private AulaH aula;

    @OneToMany(mappedBy = "alumno")
    @ToString.Exclude
    private List<CalificacionH> calificaciones;

    public String getNombreCompleto(){
        return 
                this.apellidoPaterno + " " + 
                this.apellidoMaterno + " " + 
                this.nombre;
    }

    public Integer getEdad(){
        return LocalDate.now().getYear() - this.fechaNacimiento.getYear();
    }
}
