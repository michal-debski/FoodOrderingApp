package com.example.orderservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "new_order", schema = "order_service")
public class OrderEntity {

    @Id
    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "status")
    private String status;

    @Column(name = "order_date")
    private OffsetDateTime orderDate;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private Set<OrderItemEntity> orderItems;



}
