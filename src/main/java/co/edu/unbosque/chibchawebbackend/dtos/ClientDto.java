package co.edu.unbosque.chibchawebbackend.dtos;

import co.edu.unbosque.chibchawebbackend.entities.PaquetePlanpagoEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {

    private Long id;
    private UserDto idUsuario;
    private IdentiTypeDto idTipo;
    private CategoryCDto idCategoria;
    private String numeroId;
    private String razonSocial;
    private Integer cantidadDominios;
    private PlanPaymentPackageDto idPaquetePlanpago;
    private String estado;
    private LocalDate vigencia;
    private boolean renovacionAutomatica;
    private LocalDate ultimoPagoDominio;
    private Integer cambioProducto;

}
