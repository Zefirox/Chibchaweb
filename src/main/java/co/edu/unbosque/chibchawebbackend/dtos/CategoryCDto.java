package co.edu.unbosque.chibchawebbackend.dtos;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    private Integer comision;
    private Integer precio;
}
