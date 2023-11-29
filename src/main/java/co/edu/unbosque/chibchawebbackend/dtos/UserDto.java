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
public class UserDto {
    private Long id;
    private String correo;
    private String token;
    private String telefono;
    private String direccion;
    private RegionDto idRegion;
    private String codPostal;
    private String nombre;
    private String apellido;
    private Date fechaCreacion;
    private String estado;
    private RoleDto idRol;

}
