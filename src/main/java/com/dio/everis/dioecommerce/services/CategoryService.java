package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.dto.CategoryDTO;
import com.dio.everis.dioecommerce.entities.Category;
import com.dio.everis.dioecommerce.mappers.CategoryMapper;
import com.dio.everis.dioecommerce.repositories.CategoryRepository;
import com.dio.everis.dioecommerce.services.exceptions.DataIntegrityException;
import com.dio.everis.dioecommerce.services.exceptions.ObjectAlreadyRegisteredException;
import com.dio.everis.dioecommerce.services.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public CategoryDTO update(CategoryDTO objDto) {
        find(objDto.getId());
        Category objToSave = categoryMapper.toModel(objDto);
        Category objSaved = categoryRepository.save(objToSave);
        return categoryMapper.toDto(objSaved);
    }

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    private void verifyIfIsAlreadyRegistered(Long id) {
        Optional<Category> optObj = categoryRepository.findById(id);
        if (optObj.isPresent()) {
            throw new ObjectAlreadyRegisteredException(
                    "Objeto existente! Id: " + id + ", Tipo: " + Category.class.getName()
            );
        }
    }

    public void delete(Long id) {
        find(id);
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui dependências");
        }
    }
}
