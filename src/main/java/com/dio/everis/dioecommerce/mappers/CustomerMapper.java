package com.dio.everis.dioecommerce.mappers;

import com.dio.everis.dioecommerce.dto.CustomerDTO;
import com.dio.everis.dioecommerce.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "orders", ignore = true)
    Customer toModel(CustomerDTO customerDTO);

    CustomerDTO toDto(Customer customer);
}
