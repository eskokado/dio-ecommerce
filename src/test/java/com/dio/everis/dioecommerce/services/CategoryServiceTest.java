package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.builders.CategoryDTOBuilder;
import com.dio.everis.dioecommerce.dto.CategoryDTO;
import com.dio.everis.dioecommerce.entities.Category;
import com.dio.everis.dioecommerce.mappers.CategoryMapper;
import com.dio.everis.dioecommerce.repositories.CategoryRepository;
import com.dio.everis.dioecommerce.services.exceptions.ObjectAlreadyRegisteredException;
import com.dio.everis.dioecommerce.services.exceptions.ObjectNotFoundException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Data
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    private static final Long VALID_CATEGORY_ID = 1L;
    private static final Long INVALID_CATEGORY_ID = 2L;

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void whenCategoryInformedThenItShouldBeCreated() {
        // given
        CategoryDTO expectedCategoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();
        Category expectedCategory = categoryMapper.toModel(expectedCategoryDTO);

        // when
        when(categoryRepository.findById(VALID_CATEGORY_ID)).thenReturn(Optional.empty());
        when(categoryRepository.save(expectedCategory)).thenReturn(expectedCategory);

        // then
        CategoryDTO createdCategoryDTO = categoryService.insert(expectedCategoryDTO);

        assertThat(createdCategoryDTO, is(equalTo(expectedCategoryDTO)));
    }

    @Test
    void whenAlreadyRegisteredCategoryInformedThenAnExceptionShouldBeThrown() {
        // given
        CategoryDTO expectedCategoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();
        Category expectedCategory = categoryMapper.toModel(expectedCategoryDTO);

        // when
        when(categoryRepository.findById(VALID_CATEGORY_ID)).thenReturn(Optional.of(expectedCategory));

        // then
        assertThrows(ObjectAlreadyRegisteredException.class, () -> categoryService.insert(expectedCategoryDTO));
    }

    @Test
    void whenValidCategoryIdIsGivenThenReturnAnCategory() {
        // given
        CategoryDTO expectedCategoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();
        Category expectedCategory = categoryMapper.toModel(expectedCategoryDTO);

        // when
        when(categoryRepository.findById(VALID_CATEGORY_ID)).thenReturn(Optional.of(expectedCategory));

        // then
        CategoryDTO foundCategoryDTO = categoryService.find(VALID_CATEGORY_ID);

        assertThat(foundCategoryDTO, is(equalTo(expectedCategoryDTO)));
    }

    @Test
    void whenNotRegisteredCategoryIdGivenThenThrowAnException() {
        // when
        when(categoryRepository.findById(INVALID_CATEGORY_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> categoryService.find(INVALID_CATEGORY_ID));
    }

    @Test
    void whenUpdateIsCalledWithValidCategoryGivenThenReturnACategoryUpdated() {
        // given
        CategoryDTO expectedCategoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();
        Category expectedCategory = categoryMapper.toModel(expectedCategoryDTO);

        // when
        when(categoryRepository.findById(VALID_CATEGORY_ID)).thenReturn(Optional.of(expectedCategory));
        when(categoryRepository.save(expectedCategory)).thenReturn(expectedCategory);

        // then
        CategoryDTO updateCategoryDTO = categoryService.update(expectedCategoryDTO);

        assertThat(updateCategoryDTO, is(equalTo(expectedCategoryDTO)));
    }
}
