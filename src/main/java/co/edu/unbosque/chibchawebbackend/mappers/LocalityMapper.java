package co.edu.unbosque.chibchawebbackend.mappers;

import co.edu.unbosque.chibchawebbackend.dtos.CountryDto;
import co.edu.unbosque.chibchawebbackend.dtos.RegionDto;
import co.edu.unbosque.chibchawebbackend.entities.PaisEntity;
import co.edu.unbosque.chibchawebbackend.entities.RegionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface LocalityMapper {
    @Mapping(source = "id", target = "id")
    CountryDto toCountryDto(PaisEntity paisEntity);

    @Mapping(source = "id", target = "id")
    RegionDto toRegionDto(RegionEntity regionEntity);

/*
    @Mapping(source = "idCiudad", target = "idCiudad")
    CityDto toCityDto(CiudadEntity ciudadEntity);

    List<CityDto> toCityDtoList(List<CiudadEntity> citiesEntities);
*/

    List<RegionDto> toRegionDtoList(List<RegionEntity> regionEntities);

    List<CountryDto> toCountryDtoList(List<PaisEntity> paisEntities);

}
