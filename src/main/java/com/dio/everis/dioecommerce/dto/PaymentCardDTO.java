package com.dio.everis.dioecommerce.dto;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PaymentCardDTO extends PaymentDTO {
    @NotNull
    @Min(1)
    @Max(24)
    private Integer parcelAmount;

    @Builder
    public PaymentCardDTO(Long id, Integer paymentStatus, OrderDTO order, @Min(1) @Max(24) Integer parcelAmount) {
        super(id, paymentStatus, order);
        this.parcelAmount = parcelAmount;
    }
}
