package co.edu.unbosque.chibchawebbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecaudoDto implements Serializable {
    private Long id;
    private LocalDate fecha;
    private Integer valor;
    private String descripcion;
    private ClientDto idCliente;
    private String idTransferencia;
}
