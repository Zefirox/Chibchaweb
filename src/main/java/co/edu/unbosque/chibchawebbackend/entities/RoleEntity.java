package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "rol")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", nullable = false)
    private Long id;

    @Size(max = 30)
    @Column(name = "nombre_rol", length = 30)
    private String nombre;

    @Size(max = 250)
    @Column(name = "descripcion_rol", length = 250)
    private String descripcion;

    @Size(max = 1)
    @Column(name = "estado_rol", length = 1, insertable = false)
    private String estado;

}
