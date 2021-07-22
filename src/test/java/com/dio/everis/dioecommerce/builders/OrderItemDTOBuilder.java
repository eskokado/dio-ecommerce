package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.CategoryDTO;
import com.dio.everis.dioecommerce.dto.OrderItemDTO;
import com.dio.everis.dioecommerce.dto.OrderItemPKDTO;
import com.dio.everis.dioecommerce.entities.OrderItemPK;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class OrderItemDTOBuilder {
    @Builder.Default
    private OrderItemPK id = OrderItemPK.builder().build();

    @Builder.Default
    private BigDecimal discount = BigDecimal.valueOf(10.0);

    @Builder.Default
    private Integer amount = 5;

    @Builder.Default
    private BigDecimal price = BigDecimal.valueOf(100.55);

    public OrderItemDTO toOrderItemDTO(){
        return new OrderItemDTO(id, discount, amount, price);
    }
}
