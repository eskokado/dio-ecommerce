package com.dio.everis.dioecommerce.controllers;

import com.dio.everis.dioecommerce.builders.CategoryDTOBuilder;
import com.dio.everis.dioecommerce.controllers.exceptions.ResourceExceptionHandler;
import com.dio.everis.dioecommerce.dto.CategoryDTO;
import com.dio.everis.dioecommerce.services.CategoryService;
import com.dio.everis.dioecommerce.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.dio.everis.dioecommerce.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    private static final String CATEGORY_API_URL_PATH = "/api/v1/categories";
    private static final Long VALID_CATEGORY_ID = 1L;
    private static final Long INVALID_CATEGORY_ID = 2L;

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new ResourceExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenACategoryIsCreated() throws Exception {
        // given
        CategoryDTO categoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();

        // when
        when(categoryService.insert(categoryDTO)).thenReturn(categoryDTO);

        // then
        mockMvc.perform(post(CATEGORY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(categoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(categoryDTO.getName()));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        CategoryDTO categoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();
        categoryDTO.setName(null);

        // then
        mockMvc.perform(post(CATEGORY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(categoryDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        // given
        CategoryDTO categoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();

        // when
        when(categoryService.find(VALID_CATEGORY_ID)).thenReturn(categoryDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CATEGORY_API_URL_PATH + "/" + VALID_CATEGORY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryDTO.getName()));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        when(categoryService.find(INVALID_CATEGORY_ID)).thenThrow(ObjectNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CATEGORY_API_URL_PATH + "/" + INVALID_CATEGORY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithCategoryIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        CategoryDTO categoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();

        // when
        when(categoryService.findAll()).thenReturn(Collections.singletonList(categoryDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CATEGORY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(categoryDTO.getName()));
    }

    @Test
    void thenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // when
        doNothing().when(categoryService).delete(VALID_CATEGORY_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(CATEGORY_API_URL_PATH + "/" + VALID_CATEGORY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void thenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        doThrow(ObjectNotFoundException.class).when(categoryService).delete(INVALID_CATEGORY_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(CATEGORY_API_URL_PATH + "/" + INVALID_CATEGORY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTIsCalledThenACategoryIsOkStatusReturned() throws Exception {
        // given
        CategoryDTO categoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();
        categoryDTO.setName("Modified Name");
        // when
        when(categoryService.update(categoryDTO)).thenReturn(categoryDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(CATEGORY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(categoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Modified Name")));
    }

    @Test
    void thenPUTIsCalledWithInvalidIdIsNotFoundStatusReturned() throws Exception {
        // given
        CategoryDTO categoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();

        // when
        when(categoryService.update(categoryDTO)).thenThrow(ObjectNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(CATEGORY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(categoryDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTisCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        CategoryDTO categoryDTO = CategoryDTOBuilder.builder().build().toCategoryDTO();
        categoryDTO.setName(null);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(CATEGORY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(categoryDTO)))
                .andExpect(status().isBadRequest());
    }

}
