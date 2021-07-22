package com.dio.everis.dioecommerce.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCardDTO {
    private Long id;

    private Integer paymentStatus;

    @NotNull
    @Min(1)
    @Max(24)
    private Integer parcelAmount;

    private OrderDTO order;
}
