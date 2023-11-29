package co.edu.unbosque.chibchawebbackend.dtos;

import co.edu.unbosque.chibchawebbackend.entities.ClienteEntity;
import co.edu.unbosque.chibchawebbackend.entities.MedioPagoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetodoPagoClienteDto implements Serializable {
    private Long id;
    private ClienteEntity idCliente;
    private String ultimosNumeroTarjeta;
    private String predMetodo;
    private MedioPagoEntity idMedioPago;
    private String idMetodoPagoStripe;
}
