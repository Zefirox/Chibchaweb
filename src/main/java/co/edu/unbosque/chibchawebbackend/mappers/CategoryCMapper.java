package co.edu.unbosque.chibchawebbackend.mappers;

import co.edu.unbosque.chibchawebbackend.dtos.CategoryCDto;
import co.edu.unbosque.chibchawebbackend.entities.CategoriaClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface CategoryCMapper {

    @Mapping(target = "id", source = "id")
    CategoriaClienteEntity toCategoryEntity(CategoryCDto categoryCDto);

    @Mapping(target = "id", source = "id")
    CategoryCDto toCategoryDto(CategoriaClienteEntity categoriaCliente);

    List<CategoryCDto> toListCategoryDto(List<CategoriaClienteEntity> categoriaClienteEntities);
}
