package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.OrderDTO;
import com.dio.everis.dioecommerce.dto.PaymentSlipDTO;
import com.dio.everis.dioecommerce.enums.PaymentStatus;
import lombok.Builder;

@Builder
public class PaymentSlipDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private Integer paymentStatus = PaymentStatus.PENDENTE.getCode();

    @Builder.Default
    private String dueDate = "2021-07-22";

    @Builder.Default
    private String paymentDate = "2021-08-22";

    @Builder.Default
    private OrderDTO order = OrderDTOBuilder.builder().build().toOrderDTO();

    public PaymentSlipDTO toPaymentDTO(){
        return new PaymentSlipDTO(id, paymentStatus, dueDate, paymentDate, order);
    }
}
