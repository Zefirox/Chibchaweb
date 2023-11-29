package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cliente")
public class ClienteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UserEntity idUsuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_id", nullable = false)
    private TipoIdEntity idTipo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaClienteEntity idCategoria;

    @Size(max = 20)
    @Column(name = "numero_id", length = 20)
    private String numeroId;

    @Size(max = 250)
    @Column(name = "razon_social", length = 250)
    private String razonSocial;

    @Column(name = "cantidad_dominios")
    private Integer cantidadDominios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paquete_planpago")
    private PaquetePlanpagoEntity idPaquetePlanpago;

    @Size(max = 1)
    @Column(name = "estado", length = 1, insertable = false)
    private String estado;

    @Column(name = "vigencia")
    private LocalDate vigencia;

    @Column(name = "renovacion_automatica")
    private Boolean renovacionAutomatica;

    @Column(name = "ultimo_pago_dominio")
    private LocalDate ultimoPagoDominio;

    @Column(name = "cambio_producto")
    private Integer cambioProducto;

}