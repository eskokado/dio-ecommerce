package com.dio.everis.dioecommerce.mappers;

import com.dio.everis.dioecommerce.dto.ProductDTO;
import com.dio.everis.dioecommerce.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "orders", ignore = true)
    Product toModel(ProductDTO productDTO);

    ProductDTO toDto(Product product);
}
