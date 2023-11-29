package co.edu.unbosque.chibchawebbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {

    private Long id;
    private ClientDto idCliente;
    private int valor;
    private LocalDate fecha;
}
