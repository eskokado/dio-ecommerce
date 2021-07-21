package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.CategoryDTO;
import com.dio.everis.dioecommerce.dto.StateDTO;
import lombok.Builder;

@Builder
public class StateDTOBuilder {
    @Builder.Default
    private Integer id = 1;

    @Builder.Default
    private String name = "State Name";

    public StateDTO toStateDTO(){
        return new StateDTO(id, name);
    }
}
