package co.edu.unbosque.chibchawebbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "auditoria")
public class AuditoriaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auditoria", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UserEntity idUsuario;

    @Size(max = 30)
    @Column(name = "evento_auditoria", length = 30)
    private String evento;

    @Size(max = 30)
    @Column(name = "tabla_auditoria", length = 30)
    private String tabla;

    @Column(name = "id_tabla")
    private Integer idTabla;

    @Size(max = 15)
    @Column(name = "ip", length = 15)
    private String ip;

    @Column(name = "fecha", insertable = false)
    private LocalDate fecha;

}