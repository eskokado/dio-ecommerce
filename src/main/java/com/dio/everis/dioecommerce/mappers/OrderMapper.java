package com.dio.everis.dioecommerce.mappers;

import com.dio.everis.dioecommerce.dto.OrderDTO;
import com.dio.everis.dioecommerce.dto.OrderWithPaymentCardDTO;
import com.dio.everis.dioecommerce.dto.OrderWithPaymentSlipDTO;
import com.dio.everis.dioecommerce.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "instance", source = "instance", dateFormat = "yyyy-MM-dd")
    Order toModel(OrderDTO orderDTO);
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "instance", source = "instance", dateFormat = "yyyy-MM-dd")
    Order toModel(OrderWithPaymentCardDTO orderDTO);
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "instance", source = "instance", dateFormat = "yyyy-MM-dd")
    Order toModel(OrderWithPaymentSlipDTO orderDTO);

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "instance", source = "instance", dateFormat = "yyyy-MM-dd")
    OrderDTO toDto(Order order);
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "instance", source = "instance", dateFormat = "yyyy-MM-dd")
    OrderWithPaymentCardDTO toDtoWithPaymentCard(Order order);
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "instance", source = "instance", dateFormat = "yyyy-MM-dd")
    OrderWithPaymentSlipDTO toDtoWithPaymentSlip(Order order);
}
