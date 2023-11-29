package co.edu.unbosque.chibchawebbackend.dtos;

import co.edu.unbosque.chibchawebbackend.entities.PaqueteEntity;
import co.edu.unbosque.chibchawebbackend.entities.PlanPagoEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanPaymentPackageDto {


    private Long id;

    private PackageDto idPaquete;

    private PlanPaymentDto idPlanPago;

    private Integer precio;

}
