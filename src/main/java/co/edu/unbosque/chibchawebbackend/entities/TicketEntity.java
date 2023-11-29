package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ticket")
public class TicketEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UserEntity idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario_soporte")
    private UserEntity idUsuarioSoporte;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaTicketEntity idCategoria;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_nivel_ticket", insertable = false)
    private NivelTicketEntity idNivelTicket;

    @Size(max = 250)
    @Column(name = "detalle_ticket", length = 250)
    private String detalle;

    @Column(name = "fecha_creacion_ticket", insertable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_fin_ticket")
    private LocalDate fechaFin;

    @Size(max = 25)
    @Column(name = "estado", length = 25, insertable = false)
    private String estado;

    public TicketEntity() {
        // Establecer el valor predeterminado para idNivelTicket
        this.idNivelTicket = new NivelTicketEntity();
        this.idNivelTicket.setId(1L);
        this.idUsuarioSoporte = new UserEntity();
        this.idUsuarioSoporte.setId(24L);
    }

}