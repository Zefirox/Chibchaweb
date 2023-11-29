package co.edu.unbosque.chibchawebbackend.mappers;

import co.edu.unbosque.chibchawebbackend.dtos.ClientDto;
import co.edu.unbosque.chibchawebbackend.entities.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface ClientMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "idUsuario", target = "idUsuario")
    @Mapping(source = "idTipo", target = "idTipo")
    @Mapping(source = "idCategoria.id", target = "idCategoria.id")
    @Mapping(source = "idPaquetePlanpago", target = "idPaquetePlanpago")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "vigencia", target = "vigencia")
    @Mapping(source = "renovacionAutomatica", target = "renovacionAutomatica")
    @Mapping(source = "ultimoPagoDominio", target = "ultimoPagoDominio")
    ClientDto toClientDto(ClienteEntity clienteEntity);

    List<ClientDto> toClientDtoList (List<ClienteEntity> clienteEntityList);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "idUsuario", target = "idUsuario")
    @Mapping(source = "idTipo", target = "idTipo")
    @Mapping(source = "idCategoria.id", target = "idCategoria.id")
    @Mapping(source = "idPaquetePlanpago", target = "idPaquetePlanpago")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "vigencia", target = "vigencia")
    @Mapping(source = "renovacionAutomatica", target = "renovacionAutomatica")
    @Mapping(source = "ultimoPagoDominio", target = "ultimoPagoDominio")
    ClienteEntity toClientEntity(ClientDto clienteDto);

}
