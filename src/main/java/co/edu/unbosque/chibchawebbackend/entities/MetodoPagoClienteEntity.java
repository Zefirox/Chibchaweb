package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "metodo_pago_cliente")
public class MetodoPagoClienteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private ClienteEntity idCliente;

    @Size(max = 250)
    @Column(name = "ultimos_digitos_metodo", length = 250)
    private String ultimosNumeroTarjeta;

    @Size(max = 100)
    @Column(name = "pred_metodo", length = 100)
    private String predMetodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medio_pago")
    private MedioPagoEntity idMedioPago;

    @Column(name = "id_metodo_pago_stripe", length = 60, nullable = false)
    private String idMetodoPagoStripe;

}