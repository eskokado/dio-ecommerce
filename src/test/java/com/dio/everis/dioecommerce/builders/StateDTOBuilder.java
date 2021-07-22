package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.CategoryDTO;
import com.dio.everis.dioecommerce.dto.CityDTO;
import com.dio.everis.dioecommerce.dto.StateDTO;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
public class StateDTOBuilder {
    @Builder.Default
    private Integer id = 1;

    @Builder.Default
    private String initials = "SP";

    @Builder.Default
    private String name = "State Name";

    private List<CityDTO> cities;

    public StateDTO toStateDTO(){
        return new StateDTO(id, initials, name, cities);
    }
}
