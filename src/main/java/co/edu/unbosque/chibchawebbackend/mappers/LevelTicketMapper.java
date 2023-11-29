package co.edu.unbosque.chibchawebbackend.mappers;


import co.edu.unbosque.chibchawebbackend.dtos.LevelTicketDto;
import co.edu.unbosque.chibchawebbackend.dtos.TicketDTO;
import co.edu.unbosque.chibchawebbackend.entities.NivelTicketEntity;
import co.edu.unbosque.chibchawebbackend.entities.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface LevelTicketMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "tiempoRespuesta", source = "tiempoRespuesta")
    NivelTicketEntity toUpdateTicketEntity(LevelTicketDto levelTicketDto);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "tiempoRespuesta", source = "tiempoRespuesta")
    LevelTicketDto toLevelTicketDTO (NivelTicketEntity nivelTicketEntity);
    List<LevelTicketDto> toLevelTicketDtoList (List<NivelTicketEntity> nivelTicketEntities);
}
