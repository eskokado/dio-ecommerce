package com.dio.everis.dioecommerce.mappers;

import com.dio.everis.dioecommerce.dto.CustomerDTO;
import com.dio.everis.dioecommerce.dto.CustomerWithoutAddressesDTO;
import com.dio.everis.dioecommerce.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer toModel(CustomerWithoutAddressesDTO customerDTO);

    CustomerWithoutAddressesDTO toDto(Customer customer);
}
