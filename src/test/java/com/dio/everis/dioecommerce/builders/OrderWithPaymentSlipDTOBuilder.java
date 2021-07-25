package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.*;
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
    private PaymentSlip payment = PaymentSlip.builder()
            .paymentStatus(PaymentStatus.PENDENTE)
            .dueDate(LocalDate.now())
            .paymentDate(LocalDate.now().plusDays(30))
            .build();

    @Builder.Default
    private List<OrderItemDTO> items = Collections.singletonList(OrderItemDTOBuilder.builder().build().toOrderItemDTO());

    private List<ProductDTO> products;

    public OrderDTO toOrderDTO(){
        return new OrderDTO(id, instance, customer, deliveryAddress, payment, items, products);
    }
}
