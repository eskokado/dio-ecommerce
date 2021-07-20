package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.dto.CategoryDTO;
import com.dio.everis.dioecommerce.entities.Category;
import com.dio.everis.dioecommerce.mappers.CategoryMapper;
import com.dio.everis.dioecommerce.repositories.CategoryRepository;
import com.dio.everis.dioecommerce.services.exceptions.ObjectAlreadyRegisteredException;
import com.dio.everis.dioecommerce.services.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    public CategoryDTO insert(CategoryDTO objDTO) {
        verifyIfIsAlreadyRegistered(objDTO.getId());
        Category objToSave = categoryMapper.toModel(objDTO);
        Category objSaved = categoryRepository.save(objToSave);
        return categoryMapper.toDto(objSaved);
    }

    public CategoryDTO find(Long id) {
        Category obj = categoryRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()
                ));
        return categoryMapper.toDto(obj);
    }

    private void verifyIfIsAlreadyRegistered(Long id) {
        Optional<Category> optObj = categoryRepository.findById(id);
        if (optObj.isPresent()) {
            throw new ObjectAlreadyRegisteredException(
                    "Objeto existente! Id: " + id + ", Tipo: " + Category.class.getName()
            );
        }
    }
}
