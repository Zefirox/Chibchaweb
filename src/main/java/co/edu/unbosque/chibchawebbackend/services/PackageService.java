package co.edu.unbosque.chibchawebbackend.services;

import co.edu.unbosque.chibchawebbackend.dtos.PackageDto;
import co.edu.unbosque.chibchawebbackend.entities.PaqueteEntity;
import co.edu.unbosque.chibchawebbackend.mappers.PackageMapper;
import co.edu.unbosque.chibchawebbackend.repositories.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PackageService {

    private final PackageRepository packageRepo;
    private final PackageMapper packageMapper;

    public List<PackageDto> getAllPackages(){
        List<PaqueteEntity> paqueteEntities = packageRepo.findAll();

        List<PackageDto> packageDtos = packageMapper.toPackageDtoList(paqueteEntities);

        return packageDtos;
    }

    public List<PackageDto> getAllActivePackages(){
        List<PaqueteEntity> paqueteEntities = packageRepo.findAllByEstado("A");

        List<PackageDto> packageDtos = packageMapper.toPackageDtoList(paqueteEntities);

        return packageDtos;
    }

    public PackageDto getPackageById(Long id){
        PaqueteEntity paqueteEntity = packageRepo.findById(id).get();

        return packageMapper.toPackageDto(paqueteEntity);
    }

    @Transactional
    public void updatePackageById(Long id, PackageDto newPackage){
        PaqueteEntity newPackageEntity = packageMapper.toPackageEntity(newPackage);

        PaqueteEntity oldPaqueteEntity = packageRepo.findById(id).get();

        oldPaqueteEntity.setNombre(newPackageEntity.getNombre());
        oldPaqueteEntity.setDescripcion(newPackageEntity.getDescripcion());
        oldPaqueteEntity.setEstado(newPackageEntity.getEstado());
        oldPaqueteEntity.setDominiosLimite(newPackageEntity.getDominiosLimite());

        packageRepo.save(oldPaqueteEntity);
    }

    @Transactional
    public void createPackage(PackageDto newPackage){

        PaqueteEntity newPackageEntity = packageMapper.toPackageEntity(newPackage);

        packageRepo.save(newPackageEntity);
    }

    public void deletePackage(Long packageId){
        packageRepo.deactivatePackage(packageId);
    }
}
