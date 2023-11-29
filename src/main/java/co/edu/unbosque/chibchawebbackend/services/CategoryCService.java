package co.edu.unbosque.chibchawebbackend.services;

import co.edu.unbosque.chibchawebbackend.dtos.CategoryCDto;
import co.edu.unbosque.chibchawebbackend.entities.CategoriaClienteEntity;
import co.edu.unbosque.chibchawebbackend.mappers.CategoryCMapper;
import co.edu.unbosque.chibchawebbackend.repositories.CategoryCRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryCService {

    private final CategoryCRepository categoryCRepo;
    private final CategoryCMapper categoryCMapper;

    @Transactional
    public boolean updateCategory(Long categoryId, CategoryCDto newCategory){
        CategoriaClienteEntity oldCategory = categoryCRepo.findById(categoryId).orElse(null);

        if (oldCategory!=null){
            CategoriaClienteEntity newCategoryEntity = categoryCMapper.toCategoryEntity(newCategory);

            oldCategory.setComision(newCategoryEntity.getComision());
            oldCategory.setDescripcion(newCategoryEntity.getDescripcion());
            oldCategory.setNombre(newCategoryEntity.getNombre());
            oldCategory.setEstado(newCategory.getEstado());
            oldCategory.setPrecio(newCategoryEntity.getPrecio());

            categoryCRepo.save(oldCategory);

            return true;
        }else return false;
    }

    public CategoryCDto getCategoryById(Long idCategory){
        CategoriaClienteEntity categoriaCliente = categoryCRepo.findById(idCategory).get();

        return categoryCMapper.toCategoryDto(categoriaCliente);
    }

    public List<CategoryCDto> getAllCategories(){
        List<CategoryCDto> categoryCDtos = categoryCMapper.toListCategoryDto(categoryCRepo.findAll());

        return categoryCDtos;
    }
    @Transactional
    public void createCategory(CategoryCDto newCategory){
        CategoriaClienteEntity categoriaCliente = categoryCMapper.toCategoryEntity(newCategory);

        categoryCRepo.save(categoriaCliente);
    }

    @Transactional

    public boolean deleteCategory(Long idCategory){
        CategoriaClienteEntity categoriaCliente = categoryCRepo.findById(idCategory).orElse(null);

        if (categoriaCliente!=null){
            categoriaCliente.setEstado("I");

            categoryCRepo.save(categoriaCliente);

            return true;
        }else return false;
    }
}
