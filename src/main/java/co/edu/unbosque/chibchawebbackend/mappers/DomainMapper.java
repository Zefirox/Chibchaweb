package co.edu.unbosque.chibchawebbackend.mappers;


import co.edu.unbosque.chibchawebbackend.dtos.DomainDto;
import co.edu.unbosque.chibchawebbackend.entities.DominioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface DomainMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "idCliente.id", source = "idCliente.id")
    @Mapping(target = "idCliente.idPaquetePlanpago.id", source = "idCliente.idPaquetePlanpago.id")
    @Mapping(target = "idCliente.idCategoria.id", source = "idCliente.idCategoria.id")
    DomainDto toDomainDto(DominioEntity dominioEntity);

    List<DomainDto> toDomainDtoList(List<DominioEntity> dominioEntityList);

    DominioEntity toDomainEntity(DomainDto domainDto);
}
