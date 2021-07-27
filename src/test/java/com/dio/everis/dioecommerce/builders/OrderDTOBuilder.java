package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.*;
import com.dio.everis.dioecommerce.entities.Payment;
import lombok.Builder;

import java.util.Collections;
import java.util.List;

@Builder
public class OrderDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String instance = "2021-07-22";

    @Builder.Default
    private CustomerDTO customer = CustomerDTOBuilder.builder().build().toCustomerDTO();

    @Builder.Default
    private AddressDTO deliveryAddress = AddressDTOBuilder.builder().build().toAddressDTO();

    private Payment payment;

    @Builder.Default
    private List<OrderItemDTO> items = Collections.singletonList(OrderItemDTOBuilder.builder().build().toOrderItemDTO());

    private List<ProductDTO> products;

    public OrderDTO toOrderDTO(){
        return new OrderDTO(id, instance, customer, deliveryAddress, payment, items, products);
    }
}
