package com.dio.everis.dioecommerce.mappers;

import com.dio.everis.dioecommerce.dto.StateDTO;
import com.dio.everis.dioecommerce.entities.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StateMapper {
    StateMapper INSTANCE = Mappers.getMapper(StateMapper.class);

    @Mapping(target = "cities", ignore = true)
    State toModel(StateDTO stateDTO);

    StateDTO toDto(State state);
}
