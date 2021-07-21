package com.dio.everis.dioecommerce.dto;

import com.dio.everis.dioecommerce.entities.Address;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;

    @NotNull
    @Size(min = 1, max = 150)
    private String name;

    @NotNull
    @Size(min = 1, max = 150)
    private String email;

    @NotNull
    @Size(min = 1, max = 25)
    private String cpfOrCnpj;

    @NotNull
    private Integer customerType;

    @Size(min = 1, max = 5)
    private List<AddressDTO> addresses;

    @Size(min = 1, max = 5)
    private Set<String> phones;
}
