package com.dio.everis.dioecommerce.mappers;

import com.dio.everis.dioecommerce.dto.CategoryDTO;
import com.dio.everis.dioecommerce.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "products", ignore = true)
    Category toModel(CategoryDTO categoryDTO);

    CategoryDTO toDto(Category category);
}
