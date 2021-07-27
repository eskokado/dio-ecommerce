package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.OrderDTO;
import com.dio.everis.dioecommerce.dto.PaymentCardDTO;
import com.dio.everis.dioecommerce.enums.PaymentStatus;
import lombok.Builder;

@Builder
public class PaymentCardDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private Integer paymentStatus = PaymentStatus.PENDENTE.getCode();

    @Builder.Default
    private Integer parcelAmount = 1;

    @Builder.Default
    private OrderDTO order = OrderDTOBuilder.builder().build().toOrderDTO();

    public PaymentCardDTO toPaymentDTO(){
        return new PaymentCardDTO(id, paymentStatus, order, parcelAmount);
    }
}
