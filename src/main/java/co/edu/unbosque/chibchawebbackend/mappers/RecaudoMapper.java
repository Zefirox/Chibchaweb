package co.edu.unbosque.chibchawebbackend.mappers;

import co.edu.unbosque.chibchawebbackend.dtos.RecaudoDto;
import co.edu.unbosque.chibchawebbackend.entities.RecaudoEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface RecaudoMapper {
    RecaudoEntity recaudoDtoToRecaudoEntity(RecaudoDto recaudoDto);

    RecaudoDto recaudoEntityToRecaudoDto(RecaudoEntity recaudoEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RecaudoEntity updateRecaudoEntityFromRecaudoDto(RecaudoDto recaudoDto, @MappingTarget RecaudoEntity recaudoEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "idCliente", source = "idCliente")
    List<RecaudoDto> toRecaudoDtoList(List<RecaudoEntity> recaudoEntities);
}
