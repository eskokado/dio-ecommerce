package com.dio.everis.dioecommerce.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    private Integer id;

    @NotEmpty
    @Size(min = 1, max = 3)
    private String initials;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private StateDTO state;
}
