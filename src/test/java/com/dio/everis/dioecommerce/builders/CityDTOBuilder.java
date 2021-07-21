package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.CityDTO;
import com.dio.everis.dioecommerce.dto.StateDTO;
import lombok.Builder;

@Builder
public class CityDTOBuilder {
    @Builder.Default
    private Integer id = 1;

    @Builder.Default
    private String initials = "SP";

    @Builder.Default
    private String name = "City Name";

    @Builder.Default
    private StateDTO state = StateDTOBuilder.builder().build().toStateDTO();

    public CityDTO toCityDTO(){
        return new CityDTO(id, initials, name, state);
    }
}
