package co.edu.unbosque.chibchawebbackend.dtos;

import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
}
