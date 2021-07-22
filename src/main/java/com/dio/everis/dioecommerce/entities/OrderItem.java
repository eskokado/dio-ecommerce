package com.dio.everis.dioecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDERS_ITEMS")
public class OrderItem {

    @Delegate
    @EmbeddedId
    private OrderItemPK id;

    private BigDecimal discount;
    private Integer amount;
    private BigDecimal price;

    public OrderItem(Order order, Product product, BigDecimal discount, Integer amount, BigDecimal price) {
        this.getId().setOrder(order);
        this.getId().setProduct(product);
        this.discount = discount;
        this.amount = amount;
        this.price = price;
    }

//    public Order getOrder() {
//        return id.getOrder();
//    }
//
//    public Product getProduct() {
//        return id.getProduct();
//    }
}
