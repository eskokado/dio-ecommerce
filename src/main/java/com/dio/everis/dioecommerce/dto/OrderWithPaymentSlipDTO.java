package com.dio.everis.dioecommerce.dto;

import com.dio.everis.dioecommerce.entities.Payment;
import com.dio.everis.dioecommerce.entities.PaymentSlip;
import com.dio.everis.dioecommerce.entities.Product;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderWithPaymentSlipDTO {
    private Long id;

    @NotEmpty
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
