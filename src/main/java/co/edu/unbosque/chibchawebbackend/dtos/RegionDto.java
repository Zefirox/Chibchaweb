package co.edu.unbosque.chibchawebbackend.dtos;

import co.edu.unbosque.chibchawebbackend.entities.PaisEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionDto {
    private Long id;
    private CountryDto idPais;
    private String nombre;

}
