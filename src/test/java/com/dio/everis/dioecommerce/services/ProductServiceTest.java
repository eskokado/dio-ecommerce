package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.builders.ProductDTOBuilder;
import com.dio.everis.dioecommerce.dto.ProductDTO;
import com.dio.everis.dioecommerce.entities.Product;
import com.dio.everis.dioecommerce.mappers.ProductMapper;
import com.dio.everis.dioecommerce.repositories.ProductRepository;
import com.dio.everis.dioecommerce.services.exceptions.ObjectAlreadyRegisteredException;
import com.dio.everis.dioecommerce.services.exceptions.ObjectNotFoundException;
import lombok.Data;
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
public class ProductServiceTest {
    private static final Long VALID_PRODUCT_ID = 1L;
    private static final Long INVALID_PRODUCT_ID = 2L;

    @Mock
    private ProductRepository productRepository;

    private ProductMapper productMapper = ProductMapper.INSTANCE;

    @InjectMocks
    private ProductService productService;

    @Test
    void whenProductInformedThenItShouldBeCreated() {
        // given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedProduct = productMapper.toModel(expectedProductDTO);

        // when
        when(productRepository.findById(VALID_PRODUCT_ID)).thenReturn(Optional.empty());
        when(productRepository.save(expectedProduct)).thenReturn(expectedProduct);

        // then
        ProductDTO createdProductDTO = productService.insert(expectedProductDTO);

        assertThat(createdProductDTO, is(equalTo(expectedProductDTO)));
    }

    @Test
    void whenAlreadyRegisteredProductInformedThenAnExceptionShouldBeThrown() {
        // given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedProduct = productMapper.toModel(expectedProductDTO);

        // when
        when(productRepository.findById(VALID_PRODUCT_ID)).thenReturn(Optional.of(expectedProduct));

        // then
        assertThrows(ObjectAlreadyRegisteredException.class, () -> productService.insert(expectedProductDTO));
    }

    @Test
    void whenValidProductIdIsGivenThenReturnAnProduct() {
        // given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedProduct = productMapper.toModel(expectedProductDTO);

        // when
        when(productRepository.findById(VALID_PRODUCT_ID)).thenReturn(Optional.of(expectedProduct));

        // then
        ProductDTO foundProductDTO = productService.find(VALID_PRODUCT_ID);

        assertThat(foundProductDTO, is(equalTo(expectedProductDTO)));
    }

    @Test
    void whenNotRegisteredProductIdGivenThenThrowAnException() {
        // when
        when(productRepository.findById(INVALID_PRODUCT_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> productService.find(INVALID_PRODUCT_ID));
    }

    @Test
    void whenUpdateIsCalledWithValidProductGivenThenReturnAProductUpdated() {
        // given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedProduct = productMapper.toModel(expectedProductDTO);

        // when
        when(productRepository.findById(VALID_PRODUCT_ID)).thenReturn(Optional.of(expectedProduct));
        when(productRepository.save(expectedProduct)).thenReturn(expectedProduct);

        // then
        ProductDTO updateProductDTO = productService.update(expectedProductDTO);

        assertThat(updateProductDTO, is(equalTo(expectedProductDTO)));
    }

    @Test
    void whenUpdateIsCalledWithNotExistingProductThenAnExceptionShouldBeThrown() {
        // given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // when
        when(productRepository.findById(VALID_PRODUCT_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> productService.update(expectedProductDTO));
    }

    @Test
    void whenListProductIsCalledThenReturnAListOfProduct() {
        // given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedProduct = productMapper.toModel(expectedProductDTO);

        // when
        when(productRepository.findAll()).thenReturn(Collections.singletonList(expectedProduct));

        // then
        List<ProductDTO> listProductDTO = productService.findAll();

        assertThat(listProductDTO, is(not(empty())));
        assertThat(listProductDTO.get(0), is(equalTo(expectedProductDTO)));
    }

    @Test
    void whenListProductIsCalledThenReturnAnEmptyListOfProduct() {
        // when
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        List<ProductDTO> listProductDTO = productService.findAll();

        assertThat(listProductDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAProductShouldBeDeleted() {
        // given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedProduct = productMapper.toModel(expectedProductDTO);

        //  when
        when(productRepository.findById(VALID_PRODUCT_ID)).thenReturn(Optional.of(expectedProduct));
        doNothing().when(productRepository).deleteById(VALID_PRODUCT_ID);

        // then
        productService.delete(VALID_PRODUCT_ID);

        verify(productRepository, times(1)).findById(VALID_PRODUCT_ID);
        verify(productRepository, times(1)).deleteById(VALID_PRODUCT_ID);
    }

    @Test
    void whenExclusionIsCalledWithNotExistingProductThenAnExceptionShouldBeThrown() {
        // when
        when(productRepository.findById(INVALID_PRODUCT_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> productService.delete(INVALID_PRODUCT_ID));
    }
}
