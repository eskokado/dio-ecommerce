package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.*;
import com.dio.everis.dioecommerce.entities.Payment;
import com.dio.everis.dioecommerce.entities.PaymentCard;
import com.dio.everis.dioecommerce.enums.PaymentStatus;
import lombok.Builder;

import java.util.Collections;
import java.util.List;

@Builder
public class OrderWithPaymentCardDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String instance = "2021-07-22";

    @Builder.Default
    private CustomerDTO customer = CustomerDTOBuilder.builder().build().toCustomerDTO();

    @Builder.Default
    private AddressDTO deliveryAddress = AddressDTOBuilder.builder().build().toAddressDTO();

    @Builder.Default
    private Payment payment = PaymentCard.builder()
            .id(1L)
            .parcelAmount(12)
            .build();

    @Builder.Default
    private List<OrderItemDTO> items = Collections.singletonList(OrderItemDTOBuilder.builder().build().toOrderItemDTO());

    private List<ProductDTO> products;

    public OrderWithPaymentCardDTO toOrderDTO(){
        payment.setPaymentStatus(PaymentStatus.PENDENTE.getCode());
        return new OrderWithPaymentCardDTO(id, instance, customer, deliveryAddress, payment, items, products);
    }
}
