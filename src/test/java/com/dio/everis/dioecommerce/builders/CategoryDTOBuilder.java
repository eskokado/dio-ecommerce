package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.CategoryDTO;
import com.dio.everis.dioecommerce.dto.ProductDTO;
import lombok.Builder;

import java.security.cert.CertPathBuilder;
import java.util.ArrayList;
import java.util.List;

@Builder
public class CategoryDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Category Name";

    private List<ProductDTO> products;

    public CategoryDTO toCategoryDTO(){
        return new CategoryDTO(id, name, products);
    }
}
