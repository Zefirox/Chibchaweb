package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "plan_pago")
public class PlanPagoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan_pago", nullable = false)
    private Long id;

    @Size(max = 30)
    @Column(name = "nombre_plan_pago", length = 30)
    private String nombre;

    @Size(max = 250)
    @Column(name = "descripcion_plan_pago", length = 250)
    private String descripcion;

    @Size(max = 1)
    @Column(name = "estado_plan_pago", length = 1, insertable = false)
    private String estado;

    @Column(name = "meses")
    private Integer meses;

}