package co.edu.unbosque.chibchawebbackend.mappers;

import co.edu.unbosque.chibchawebbackend.dtos.PackageDto;
import co.edu.unbosque.chibchawebbackend.entities.PaqueteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface PackageMapper {

    @Mapping(target = "id", source = "id")
    PackageDto toPackageDto(PaqueteEntity paqueteEntity);

    @Mapping(target = "id", source = "id")
    PaqueteEntity toPackageEntity(PackageDto packageDto);

    @Mapping(target = "id", source = "id")
    List<PackageDto> toPackageDtoList(List<PaqueteEntity> paqueteEntities);
}
