package co.edu.unbosque.chibchawebbackend.mappers;

import co.edu.unbosque.chibchawebbackend.dtos.CategoryCDto;
import co.edu.unbosque.chibchawebbackend.dtos.PackageDto;
import co.edu.unbosque.chibchawebbackend.entities.CategoriaClienteEntity;
import co.edu.unbosque.chibchawebbackend.entities.PaqueteEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface ProductMapper {

    List<CategoryCDto> toCategoryCDtoList(List<CategoriaClienteEntity> list);

    List<PackageDto> toPackageDtoList(List<PaqueteEntity> list);
}
