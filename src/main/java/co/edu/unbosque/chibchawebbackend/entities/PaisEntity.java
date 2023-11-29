package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "pais")
public class PaisEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pais", nullable = false)
    private Long id;

    @Size(max = 30)
    @Column(name = "nombre_pais", length = 30)
    private String nombre;

    @Size(max = 1)
    @Column(name = "estado_pais", length = 1, insertable = false)
    private String estado;

}