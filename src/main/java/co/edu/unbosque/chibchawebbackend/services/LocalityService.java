package co.edu.unbosque.chibchawebbackend.services;

import co.edu.unbosque.chibchawebbackend.dtos.CountryDto;
import co.edu.unbosque.chibchawebbackend.dtos.RegionDto;
import co.edu.unbosque.chibchawebbackend.entities.PaisEntity;
import co.edu.unbosque.chibchawebbackend.entities.RegionEntity;
import co.edu.unbosque.chibchawebbackend.mappers.LocalityMapper;
import co.edu.unbosque.chibchawebbackend.repositories.CountryRepository;
import co.edu.unbosque.chibchawebbackend.repositories.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class LocalityService {

    private final CountryRepository countryRepo;
    private final LocalityMapper localityMapper;
    private final RegionRepository regionRepo;

    //@Query("SELECT * FROM RegionEntity where idPais = :idCountry")
    public List<RegionDto> getRegions(@Param("idCountry") Long idCountry){
        List<RegionEntity> regionEntities = regionRepo.findByIdPais(countryRepo.findById(idCountry).get());
        return localityMapper.toRegionDtoList(regionEntities);
    }

    //@Query("SELECT * FROM CiudadEntity where idRegion = :idRegionL")
    /*public List<CityDto> getCities(@Param("idRegionL") Long idRegionL){
        List<CiudadEntity> citiesEntities = cityRepo.findByIdRegion(regionRepo.findById(idRegionL).get());
        return localityMapper.toCityDtoList(citiesEntities);
    }*/

    public List<CountryDto> getCountries(){
        List<PaisEntity> countryEntities = countryRepo.findAll();
        return localityMapper.toCountryDtoList(countryEntities);
    }

}
