package com.dio.everis.dioecommerce.dto;

import com.dio.everis.dioecommerce.entities.Payment;
import com.dio.everis.dioecommerce.entities.Product;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;

    @NotNull
    private String instance;

    @NotNull
    private CustomerDTO customer;

    @NotNull
    private AddressDTO deliveryAddress;

    @NotNull
    private Payment payment;

    @NotNull
    private List<OrderItemDTO> items;

    private List<ProductDTO> products;
}