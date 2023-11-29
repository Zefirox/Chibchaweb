package co.edu.unbosque.chibchawebbackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayMethodClientDto {

    private Long id;
    private ClientDto idCliente;
    private String ultimosNumeroTarjeta;
    private String predMetodo;
    private PaymentTypeDto idMedioPago;
    private String idMetodoPagoStripe;

}
