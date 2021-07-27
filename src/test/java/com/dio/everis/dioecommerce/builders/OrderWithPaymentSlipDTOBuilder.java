package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.*;
import com.dio.everis.dioecommerce.entities.Payment;
import com.dio.everis.dioecommerce.entities.PaymentSlip;
import com.dio.everis.dioecommerce.enums.PaymentStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Builder
public class OrderWithPaymentSlipDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String instance = "2021-07-22";

    @Builder.Default
    private CustomerDTO customer = CustomerDTOBuilder.builder().build().toCustomerDTO();

    @Builder.Default
    private AddressDTO deliveryAddress = AddressDTOBuilder.builder().build().toAddressDTO();

    @Builder.Default
    private Payment payment = PaymentSlip.builder()
        .id(1L)
        .paymentDate(LocalDate.parse("2021-07-27"))
        .dueDate(LocalDate.parse("2021-07-27"))
        .build();

    @Builder.Default
    private List<OrderItemDTO> items = Collections.singletonList(OrderItemDTOBuilder.builder().build().toOrderItemDTO());

    private List<ProductDTO> products;

    public OrderWithPaymentSlipDTO toOrderDTO(){
        payment.setPaymentStatus(PaymentStatus.PENDENTE.getCode());
        return new OrderWithPaymentSlipDTO(id, instance, customer, deliveryAddress, payment, items, products);
    }
}
