package co.edu.unbosque.chibchawebbackend.entities;

import co.edu.unbosque.chibchawebbackend.dtos.ClientDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "pago")
public class PagoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private ClienteEntity idCliente;

    @Column(name = "valor_pago")
    private Integer valor;

    @Column(name = "fecha_pago")
    private LocalDate fecha;
}