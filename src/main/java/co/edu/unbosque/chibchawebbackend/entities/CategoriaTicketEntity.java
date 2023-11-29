package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "categoria_ticket")
public class CategoriaTicketEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private Long id;

    @Size(max = 30)
    @Column(name = "nombre_categoria_ticket", length = 30)
    private String nombre;

    @Size(max = 30)
    @Column(name = "descripcion_categoria_ticket", length = 30)
    private String descripcion;

}