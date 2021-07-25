package com.dio.everis.dioecommerce.dto;

import com.dio.everis.dioecommerce.entities.Order;
import com.dio.everis.dioecommerce.entities.OrderItem;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String name;

    @DecimalMin(value = "0.01")
    @DecimalMin(value = "99999.99")
    private BigDecimal price;

    @Size(min = 1, max = 10)
    private List<CategoryDTO> categories;

    private Set<OrderItemDTO> items;

    private List<Order> orders;
}
