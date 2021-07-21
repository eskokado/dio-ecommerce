package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.CategoryDTO;
import com.dio.everis.dioecommerce.dto.ProductDTO;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Builder
public class ProductDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Product Name";

    @Builder.Default
    private BigDecimal price = BigDecimal.valueOf(15.55);

    @Builder.Default
    private List<CategoryDTO> categories = Collections.singletonList(CategoryDTOBuilder.builder().build().toCategoryDTO());

    public ProductDTO toProductDTO(){
        return new ProductDTO(id, name, price, categories);
    }
}
