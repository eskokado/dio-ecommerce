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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    void whenUpdateIsCalledWithNotExistingCategoryThenAnExceptionShouldBeThrown() {
        // given
        CategoryDTO expectedCategoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();

        // when
        when(categoryRepository.findById(VALID_CATEGORY_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> categoryService.update(expectedCategoryDTO));
    }

    @Test
    void whenListCategoryIsCalledThenReturnAListOfCategory() {
        // given
        CategoryDTO expectedCategoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();
        Category expectedCategory = categoryMapper.toModel(expectedCategoryDTO);

        // when
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(expectedCategory));

        // then
        List<CategoryDTO> listCategoryDTO = categoryService.findAll();

        assertThat(listCategoryDTO, is(not(empty())));
        assertThat(listCategoryDTO.get(0), is(equalTo(expectedCategoryDTO)));
    }

    @Test
    void whenListCategoryIsCalledThenReturnAnEmptyListOfCategory() {
        // when
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        List<CategoryDTO> listCategoryDTO = categoryService.findAll();

        assertThat(listCategoryDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenACategoryShouldBeDeleted() {
        // given
        CategoryDTO expectedCategoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();
        Category expectedCategory = categoryMapper.toModel(expectedCategoryDTO);

        //  when
        when(categoryRepository.findById(VALID_CATEGORY_ID)).thenReturn(Optional.of(expectedCategory));
        doNothing().when(categoryRepository).deleteById(VALID_CATEGORY_ID);

        // then
        categoryService.delete(VALID_CATEGORY_ID);

        verify(categoryRepository, times(1)).findById(VALID_CATEGORY_ID);
        verify(categoryRepository, times(1)).deleteById(VALID_CATEGORY_ID);
    }

    @Test
    void whenExclusionIsCalledWithNotExistingCategoryThenAnExceptionShouldBeThrown() {
        // when
        when(categoryRepository.findById(INVALID_CATEGORY_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> categoryService.delete(INVALID_CATEGORY_ID));
    }
}
