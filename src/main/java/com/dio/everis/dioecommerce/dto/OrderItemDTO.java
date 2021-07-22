package com.dio.everis.dioecommerce.dto;

import com.dio.everis.dioecommerce.entities.OrderItemPK;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private OrderItemPK id;

    @DecimalMin(value = "0.01")
    @DecimalMin(value = "99999.99")
    private BigDecimal discount;

    @Min(1)
    @Max(99)
    private Integer amount;

    @DecimalMin(value = "0.01")
    @DecimalMin(value = "99999.99")
    private BigDecimal price;
}
