package com.dio.everis.dioecommerce.mappers;

import com.dio.everis.dioecommerce.dto.CategoryDTO;
import com.dio.everis.dioecommerce.dto.OrderDTO;
import com.dio.everis.dioecommerce.dto.OrderWithPaymentCardDTO;
import com.dio.everis.dioecommerce.dto.OrderWithPaymentSlipDTO;
import com.dio.everis.dioecommerce.entities.Category;
import com.dio.everis.dioecommerce.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "products", ignore = true)
    Order toModel(OrderDTO orderDTO);
    @Mapping(target = "products", ignore = true)
    Order toModel(OrderWithPaymentCardDTO orderDTO);
    @Mapping(target = "products", ignore = true)
    Order toModel(OrderWithPaymentSlipDTO orderDTO);

    @Mapping(target = "products", ignore = true)
    OrderDTO toDto(Order order);
    @Mapping(target = "products", ignore = true)
    OrderWithPaymentCardDTO toDtoWithPaymentCard(Order order);
    @Mapping(target = "products", ignore = true)
    OrderWithPaymentSlipDTO toDtoWithPaymentSlip(Order order);
}
