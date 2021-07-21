package com.dio.everis.dioecommerce.mappers;

import com.dio.everis.dioecommerce.dto.AddressDTO;
import com.dio.everis.dioecommerce.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address toModel(AddressDTO addressDTO);

    AddressDTO toDto(Address address);
}
