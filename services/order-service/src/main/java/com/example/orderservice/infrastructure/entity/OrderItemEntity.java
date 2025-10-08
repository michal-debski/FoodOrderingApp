package com.example.orderservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item", schema = "order_service")
public class OrderItemEntity {
    @Id
    @Column(name = "order_item_id")
    private String orderItemId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "meal_id")
    private String mealId;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_number")
    private OrderEntity order;


}
