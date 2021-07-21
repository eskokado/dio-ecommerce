package com.dio.everis.dioecommerce.mappers;

import com.dio.everis.dioecommerce.dto.StateDTO;
import com.dio.everis.dioecommerce.entities.State;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StateMapper {
    StateMapper INSTANCE = Mappers.getMapper(StateMapper.class);

    State toModel(StateDTO stateDTO);

    StateDTO toDto(State state);
}
