package com.dio.everis.dioecommerce.dto;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PaymentSlipDTO  extends PaymentDTO{
    @NotEmpty
    private String dueDate;

    @NotEmpty
    private String paymentDate;

    @Builder
    public PaymentSlipDTO(Long id, Integer paymentStatus, OrderDTO order, @NotEmpty String dueDate, @NotEmpty String paymentDate) {
        super(id, paymentStatus, order);
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
    }
}
