package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "usuario")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_Region", nullable = false)
    private RegionEntity idRegion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private RoleEntity idRol;

    @Size(max = 90)
    @Column(name = "correo_usuario", length = 90, unique = true)
    private String correo;

    @Size(max = 250)
    @Column(name = "clave_usuario", length = 250)
    private String clave;

    @Size(max = 15)
    @Column(name = "telefono_usuario", length = 15)
    private String telefono;

    @Size(max = 70)
    @Column(name = "direccion_usuario", length = 70)
    private String direccion;

    @Size(max = 30)
    @Column(name = "cod_postal_usuario", length = 30)
    private String codPostal;

    @Size(max = 50)
    @Column(name = "nombre_usuario", length = 50)
    private String nombre;

    @Size(max = 50)
    @Column(name = "apellido_usuario", length = 50)
    private String apellido;

    @Column(name = "fecha_creacion_usuario", insertable = false)
    private Instant fechaCreacion;

    @Size(max = 1)
    @Column(name = "estado_usuario", length = 1, insertable = false)
    private String estado;


}
