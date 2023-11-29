package co.edu.unbosque.chibchawebbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignUpDto {
    private String correo;
    private char[] clave;
    private String telefono;
    private String direccion;
    private RegionDto region;
    private String codPostal;
    private String nombre;
    private String apellido;
    private Date fechaCreacion;
    private RoleDto idRol;

    private UserDto idUsuario;
    private IdentiTypeDto idTipo;
    private CategoryCDto idCategoria;
    private String numeroId;
    private String razonSocial;
    private Integer cantidadDominios;
}
