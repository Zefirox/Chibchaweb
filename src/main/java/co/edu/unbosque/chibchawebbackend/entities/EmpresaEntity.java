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
@Table(name = "empresa")
public class EmpresaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_region", nullable = false)
    private RegionEntity idRegion;

    @Size(max = 150)
    @Column(name = "razon_social_empresa", length = 150)
    private String razonSocial;

    @Size(max = 20)
    @Column(name = "nit_empresa", length = 20)
    private String nit;

    @Size(max = 15)
    @Column(name = "telefono_empresa", length = 15)
    private String telefono;

    @Size(max = 200)
    @Column(name = "direccion_empresa", length = 200)
    private String direccion;

    @Size(max = 100)
    @Column(name = "correo_empresa", length = 100)
    private String correo;

    @Column(name = "fecha_creacion_empresa", insertable = false)
    private LocalDate fechaCreacion;

    @Size(max = 1)
    @Column(name = "estado_empresa", length = 1, insertable = false)
    private String estado;

}