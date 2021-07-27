package com.dio.everis.dioecommerce.mappers;

import com.dio.everis.dioecommerce.dto.PaymentCardDTO;
import com.dio.everis.dioecommerce.dto.PaymentDTO;
import com.dio.everis.dioecommerce.dto.PaymentSlipDTO;
import com.dio.everis.dioecommerce.entities.Payment;
import com.dio.everis.dioecommerce.entities.PaymentCard;
import com.dio.everis.dioecommerce.entities.PaymentSlip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

//    @Mapping(target = "products", ignore = true)
//    @Mapping(target = "instance", source = "instance", dateFormat = "yyyy-MM-dd")
//    Payment toModel(PaymentDTO paymentDTO);
//    @Mapping(target = "products", ignore = true)
//    @Mapping(target = "instance", source = "instance", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "parcelAmount", source = "parcelAmount")
    PaymentCard toModel(PaymentCardDTO paymentDTO);
//    @Mapping(target = "products", ignore = true)
//    @Mapping(target = "instance", source = "instance", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "dueDate", source = "dueDate")
    @Mapping(target = "paymentDate", source = "paymentDate")
    PaymentSlip toModel(PaymentSlipDTO paymentDTO);

//    @Mapping(target = "products", ignore = true)
//    @Mapping(target = "instance", source = "instance", dateFormat = "yyyy-MM-dd")
//    PaymentDTO toDto(Payment payment);
//    @Mapping(target = "products", ignore = true)
//    @Mapping(target = "instance", source = "instance", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "parcelAmount", source = "parcelAmount")
    PaymentCardDTO toDtoWithPaymentCard(PaymentCard payment);
//    @Mapping(target = "products", ignore = true)
//    @Mapping(target = "instance", source = "instance", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "dueDate", source = "dueDate")
    @Mapping(target = "paymentDate", source = "paymentDate")
    PaymentSlipDTO toDtoWithPaymentSlip(PaymentSlip payment);
}
