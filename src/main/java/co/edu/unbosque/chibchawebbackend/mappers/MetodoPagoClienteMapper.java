package co.edu.unbosque.chibchawebbackend.mappers;

import co.edu.unbosque.chibchawebbackend.dtos.MetodoPagoClienteDto;
import co.edu.unbosque.chibchawebbackend.entities.MetodoPagoClienteEntity;
import org.mapstruct.*;

@Mapper(componentModel = "Spring")
public interface MetodoPagoClienteMapper {
    MetodoPagoClienteEntity metodoPagoClienteDtoToMetodoPagoClienteEntity(MetodoPagoClienteDto metodoPagoClienteDto);

    MetodoPagoClienteDto metodoPagoClienteEntityToMetodoPagoClienteDto(MetodoPagoClienteEntity metodoPagoClienteEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MetodoPagoClienteEntity updateMetodoPagoClienteEntityFromMetodoPagoClienteDto(MetodoPagoClienteDto metodoPagoClienteDto, @MappingTarget MetodoPagoClienteEntity metodoPagoClienteEntity);
}
