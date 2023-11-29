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
@Table(name = "recaudo")
public class RecaudoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recaudo", nullable = false)
    private Long id;

    @Column(name = "fecha_recaudo", insertable = false)
    private LocalDate fecha;

    @Column(name = "valor_recaudo")
    private Integer valor;

    @Size(max = 40)
    @Column(name = "descripcion_recaudo", length = 40)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private ClienteEntity idCliente;

    @Size(max = 40)
    @Column(name = "id_transferencia")
    private String idTransferencia;

}