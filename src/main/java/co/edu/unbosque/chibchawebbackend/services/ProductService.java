package co.edu.unbosque.chibchawebbackend.services;

import co.edu.unbosque.chibchawebbackend.dtos.CategoryCDto;
import co.edu.unbosque.chibchawebbackend.dtos.PackageDto;
import co.edu.unbosque.chibchawebbackend.entities.CategoriaClienteEntity;
import co.edu.unbosque.chibchawebbackend.entities.PaqueteEntity;
import co.edu.unbosque.chibchawebbackend.mappers.ProductMapper;
import co.edu.unbosque.chibchawebbackend.repositories.CategoryCRepository;
import co.edu.unbosque.chibchawebbackend.repositories.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final CategoryCRepository categoryCRepo;
    private final ProductMapper productMapper;
    private final PackageRepository packageRepository;


    public List<CategoryCDto> getCategoriesC(){
        List<CategoriaClienteEntity> categoryList = categoryCRepo.findAll();
        return productMapper.toCategoryCDtoList(categoryList);
    }

    public List<PackageDto> getPackages(){
        List<PaqueteEntity> packageList = packageRepository.findAll();
        return productMapper.toPackageDtoList(packageList);
    }


}
