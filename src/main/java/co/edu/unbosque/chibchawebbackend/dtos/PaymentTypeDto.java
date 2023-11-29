package co.edu.unbosque.chibchawebbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentTypeDto {

    private Long id;
    private String tipoMedioPago;
    private String estado;
}
