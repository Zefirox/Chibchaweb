package co.edu.unbosque.chibchawebbackend.dtos;

import co.edu.unbosque.chibchawebbackend.entities.ClienteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DomainDto {

     private Long id;
     private ClientDto idCliente;
     private String nombre;
     private String estado;
}
