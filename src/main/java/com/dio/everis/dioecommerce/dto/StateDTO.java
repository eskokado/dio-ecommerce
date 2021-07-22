package com.dio.everis.dioecommerce.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateDTO {
    private Integer id;

    @NotNull
    @Size(min = 2, max = 2)
    private String initials;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private List<CityDTO> cities;
}
