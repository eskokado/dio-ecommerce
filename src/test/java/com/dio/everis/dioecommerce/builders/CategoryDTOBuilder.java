package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.CategoryDTO;
import lombok.Builder;

import java.security.cert.CertPathBuilder;

@Builder
public class CategoryDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Category Name";

    public CategoryDTO toCategoryDTO(){
        return new CategoryDTO(id, name);
    }
}
