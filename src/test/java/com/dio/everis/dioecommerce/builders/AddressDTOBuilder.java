package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.*;
import lombok.Builder;

@Builder
public class AddressDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String publicPlace = "Public Place";

    @Builder.Default
    private String number = "123456";

    @Builder.Default
    private String complement = "Ap 102";

    @Builder.Default
    private String district = "123456";

    @Builder.Default
    private String zipCode = "123456";

    @Builder.Default
    private CityDTO city = CityDTOBuilder.builder().build().toCityDTO();

    @Builder.Default
    private CustomerWithoutAddressesDTO customer = CustomerDTOBuilder.builder().build().toCustomerDTO();

    public AddressDTO toAddressDTO(){
        return new AddressDTO(id, publicPlace, number, complement, district, zipCode, city, customer);
    }
}
