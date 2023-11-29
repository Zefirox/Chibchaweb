package co.edu.unbosque.chibchawebbackend.mappers;

import co.edu.unbosque.chibchawebbackend.dtos.*;
import co.edu.unbosque.chibchawebbackend.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface TicketMapper {

    /*
    @Mapping(target = "id", source = "id")

    CategoryTicketDto toCategoryTicketDto (CategoriaTicketEntity categoriaTicketEntity);
    List<CategoryTicketDto> toCategoryTicketDtoList (List<CategoriaTicketEntity> categoriaTicketEntities);

    @Mapping(target = "id", source = "id")
    LevelTicketDto toLevelTicketDto (NivelTicketEntity nivelTicketEntity);
    List<LevelTicketDto> toLevelTicketDtotoList (List<NivelTicketEntity> nivelTicketEntities);
    */

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idNivelTicket", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaFin", ignore = true)
    @Mapping(target = "idUsuarioSoporte")
    @Mapping(target = "detalle", source = "detalleTicket")
    TicketEntity toTicketEntity(TicketDTO ticketDto);

    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "detalle", source = "detalleTicket")
    @Mapping(target = "fechaCreacion", source = "fechaCreacionTicket")
    @Mapping(target = "fechaFin", source = "fechaFinalTicket")
    @Mapping(target = "idUsuarioSoporte", source = "idUsuarioSoporte")
    TicketEntity toUpdateTicketEntity(TicketDTO ticketDto);



    @Mapping(target = "id", source = "id")
    @Mapping(target = "idUsuario.id", source = "idUsuario.id")
    @Mapping(target = "idUsuarioSoporte.id", source = "idUsuarioSoporte.id")
    @Mapping(target = "idCategoria.id", source = "idCategoria.id")
    @Mapping(target = "idNivelTicket.id", source = "idNivelTicket.id")
    @Mapping(target = "detalleTicket", source = "detalle")
    @Mapping(target = "fechaCreacionTicket", source = "fechaCreacion")
    @Mapping(target = "fechaFinalTicket", source = "fechaFin")
    @Mapping(target = "estado", source = "estado")

    TicketDTO toTicketDTO (TicketEntity ticketEntity);
    List<TicketDTO> toTicketDtoList (List<TicketEntity> ticketEntities);
}
