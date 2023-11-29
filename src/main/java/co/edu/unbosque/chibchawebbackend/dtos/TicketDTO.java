package co.edu.unbosque.chibchawebbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDTO {
    private Long id;
    private UserDto idUsuario;
    private UserDto idUsuarioSoporte;
    private CategoryTicketDto idCategoria;
    private LevelTicketDto idNivelTicket;
    private String detalleTicket;
    private Date fechaCreacionTicket;
    private Date fechaFinalTicket;
    private String estado;
}



