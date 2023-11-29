package co.edu.unbosque.chibchawebbackend.dtos;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LevelTicketDto{
    Long id;
    String nombre;
    Integer tiempoRespuesta;
}