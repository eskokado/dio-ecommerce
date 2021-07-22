package com.dio.everis.dioecommerce.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSlipDTO {
    private Long id;

    private Integer paymentStatus;

    @NotNull
    private String dueDate;

    @NotNull
    private String paymentDate;

    private OrderDTO order;
}
