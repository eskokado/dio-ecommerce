package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.AddressDTO;
import com.dio.everis.dioecommerce.dto.CustomerWithoutAddressesDTO;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Builder
public class CustomerDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Customer Name";

    @Builder.Default
    private String email = "user@email.com";

    @Builder.Default
    private String cpfOrCnpj = "09588999987";

    @Builder.Default
    private Integer customerType = 1;

    @Builder.Default
    private Set<String> phones = Collections.singleton("11209938467");

    public CustomerWithoutAddressesDTO toCustomerDTO(){
        return new CustomerWithoutAddressesDTO(id, name, email, cpfOrCnpj, customerType, phones);
    }
}
