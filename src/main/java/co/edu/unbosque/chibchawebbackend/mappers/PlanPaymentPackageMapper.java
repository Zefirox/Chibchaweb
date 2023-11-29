package co.edu.unbosque.chibchawebbackend.mappers;

import co.edu.unbosque.chibchawebbackend.dtos.PlanPaymentPackageDto;
import co.edu.unbosque.chibchawebbackend.entities.PaquetePlanpagoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface PlanPaymentPackageMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "idPaquete", source = "idPaquete")
    @Mapping(target = "idPlanPago", source = "idPlanPago")
    PaquetePlanpagoEntity toEntity(PlanPaymentPackageDto planPaymentPackageDto);

}
