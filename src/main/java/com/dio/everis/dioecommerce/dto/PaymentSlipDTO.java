package com.dio.everis.dioecommerce.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSlipDTO {
    private Long id;

    private Integer paymentStatus;

    @NotEmpty
    private String dueDate;

    @NotEmpty
    private String paymentDate;

    private OrderDTO order;
}
