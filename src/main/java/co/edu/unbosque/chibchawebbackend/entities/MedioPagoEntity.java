package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "medio_pago")
public class MedioPagoEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medio", nullable = false)
    private Long id;

    @Size(max = 250)
    @Column(name = "tipo_medio_pago", length = 250)
    private String medioPago;

    @Size(max = 1)
    @Column(name = "estado_medio_pago", length = 1, insertable = false)
    private String estado;

}