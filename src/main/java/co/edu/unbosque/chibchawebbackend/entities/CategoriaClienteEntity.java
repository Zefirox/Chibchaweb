package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "categoria_cliente")
public class CategoriaClienteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private Long id;

    @Size(max = 30)
    @Column(name = "nombre_categoria", length = 30)
    private String nombre;

    @Size(max = 250)
    @Column(name = "descripcion_categoria", length = 250)
    private String descripcion;

    @Size(max = 1)
    @Column(name = "estado_categoria", length = 1, insertable = false)
    private String estado;

    @Column(name = "comision")
    private Integer comision;

    @Column(name = "precio")
    private Integer precio;

}