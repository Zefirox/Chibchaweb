package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "nivel_ticket")
public class NivelTicketEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nivel_ticket", nullable = false)
    private Long id;

    @Size(max = 30)
    @Column(name = "nombre_nivel_ticket", length = 30)
    private String nombre;

    @Column(name = "tiempo_respuesta_nivel_ticket")
    private Integer tiempoRespuesta;

}