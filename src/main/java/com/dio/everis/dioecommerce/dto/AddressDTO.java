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
public class AddressDTO {
    private Long id;

    @NotEmpty
    private String publicPlace;

    @NotEmpty
    @Size(min = 1, max = 30)
    private String number;

    private String complement;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String district;

    @NotEmpty
    @Size(min = 1, max = 30)
    private String zipCode;

    @NotNull
    private CityDTO city;

    @NotNull
    private CustomerDTO customer;
}
