package com.dio.everis.dioecommerce.controllers;

import com.dio.everis.dioecommerce.builders.ProductDTOBuilder;
import com.dio.everis.dioecommerce.controllers.exceptions.ResourceExceptionHandler;
import com.dio.everis.dioecommerce.dto.ProductDTO;
import com.dio.everis.dioecommerce.services.ProductService;
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

import java.math.BigDecimal;
import java.util.Collections;

import static com.dio.everis.dioecommerce.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    private static final String PRODUCT_API_URL_PATH = "/api/v1/products";
    private static final Long VALID_PRODUCT_ID = 1L;
    private static final Long INVALID_PRODUCT_ID = 2L;

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new ResourceExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAProductIsCreated() throws Exception {
        // given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // when
        when(productService.insert(productDTO)).thenReturn(productDTO);

        // then
        mockMvc.perform(post(PRODUCT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productDTO.getName()))
                .andExpect(jsonPath("$.price").value(productDTO.getPrice().toString()));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setName(null);

        // then
        mockMvc.perform(post(PRODUCT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        // given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // when
        when(productService.find(VALID_PRODUCT_ID)).thenReturn(productDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCT_API_URL_PATH + "/" + VALID_PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDTO.getName()))
                .andExpect(jsonPath("$.price").value(productDTO.getPrice().toString()));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        when(productService.find(INVALID_PRODUCT_ID)).thenThrow(ObjectNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCT_API_URL_PATH + "/" + INVALID_PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithProductIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // when
        when(productService.findAll()).thenReturn(Collections.singletonList(productDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(productDTO.getName()))
                .andExpect(jsonPath("$[0].price").value(productDTO.getPrice().toString()));
    }

    @Test
    void thenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // when
        doNothing().when(productService).delete(VALID_PRODUCT_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(PRODUCT_API_URL_PATH + "/" + VALID_PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void thenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        doThrow(ObjectNotFoundException.class).when(productService).delete(INVALID_PRODUCT_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(PRODUCT_API_URL_PATH + "/" + INVALID_PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTIsCalledThenAProductIsOkStatusReturned() throws Exception {
        // given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setName("Modified Name");
        productDTO.setPrice(BigDecimal.valueOf(29.99));
        // when
        when(productService.update(productDTO)).thenReturn(productDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(PRODUCT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Modified Name")))
                .andExpect(jsonPath("$.price").value(productDTO.getPrice().toString()));
    }

    @Test
    void thenPUTIsCalledWithInvalidIdIsNotFoundStatusReturned() throws Exception {
        // given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // when
        when(productService.update(productDTO)).thenThrow(ObjectNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(PRODUCT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTisCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setName(null);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(PRODUCT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isBadRequest());
    }

}
