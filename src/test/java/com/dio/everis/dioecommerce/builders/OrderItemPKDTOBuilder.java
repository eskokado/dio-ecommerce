package com.dio.everis.dioecommerce.builders;

import com.dio.everis.dioecommerce.dto.OrderDTO;
import com.dio.everis.dioecommerce.dto.OrderItemPKDTO;
import com.dio.everis.dioecommerce.dto.ProductDTO;
import lombok.Builder;

@Builder
public class OrderItemPKDTOBuilder {
    @Builder.Default
    private OrderDTO order = OrderDTOBuilder.builder().build().toOrderDTO();

    @Builder.Default
    private ProductDTO product = ProductDTOBuilder.builder().build().toProductDTO();

    public OrderItemPKDTO toOrderItemPKDTO(){
        return new OrderItemPKDTO(order, product);
    }
}
