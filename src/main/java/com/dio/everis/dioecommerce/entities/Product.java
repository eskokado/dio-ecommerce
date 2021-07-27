package com.dio.everis.dioecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.OrderedSetType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;

    @ManyToMany
    @JoinTable(
            name = "PRODUCTS_CATEGORIES",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @OneToMany(mappedBy = "id.product")
    private Set<OrderItem> items;

    private List<Order> orders;

    public List<Order> getOrders() {
        if (items == null) return new ArrayList<>();
        return getItems().stream().map(OrderItem::getOrder).collect(Collectors.toList());
    }
}
