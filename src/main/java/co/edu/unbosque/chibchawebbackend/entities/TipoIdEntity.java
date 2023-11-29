package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "tipo_id")
public class TipoIdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_id", nullable = false)
    private Long id;

    @Size(max = 30)
    @Column(name = "nombre_tipo_id", length = 30)
    private String nombre;

    @Size(max = 250)
    @Column(name = "descripcion_tipo_id", length = 250)
    private String descripcion;

    @Size(max = 1)
    @Column(name = "estado_tipo_id", length = 1, insertable = false)
    private String estado;

}