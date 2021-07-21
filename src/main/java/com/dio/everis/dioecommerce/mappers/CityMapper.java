package com.dio.everis.dioecommerce.mappers;

import com.dio.everis.dioecommerce.dto.CityDTO;
import com.dio.everis.dioecommerce.entities.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    City toModel(CityDTO cityDTO);

    CityDTO toDto(City city);
}
