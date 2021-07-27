package com.dio.everis.dioecommerce.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class PaymentDTO {
    private Long id;

    private Integer paymentStatus;

    private OrderDTO order;
}
