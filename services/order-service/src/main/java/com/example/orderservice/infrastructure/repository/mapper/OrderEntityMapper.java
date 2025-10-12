package com.example.orderservice.infrastructure.repository.mapper;


import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderItem;
import com.example.orderservice.infrastructure.entity.OrderEntity;
import com.example.orderservice.infrastructure.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderEntityMapper {

    public OrderEntity mapToEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNumber(order.getOrderNumber());
        orderEntity.setClientEmail(order.getCustomerEmail());
        orderEntity.setRestaurantId(order.getRestaurantId());
        orderEntity.setOrderDate(order.getOrderDate());
        orderEntity.setStatus(order.getStatus());
        orderEntity.setTotalPrice(order.getTotalPrice());

        Set<OrderItemEntity> items = new HashSet<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            OrderItemEntity itemEntity = new OrderItemEntity();
            itemEntity.setOrderItemId(UUID.randomUUID().toString());
            itemEntity.setMealId(orderItem.getMealId());
            itemEntity.setQuantity(orderItem.getQuantity());
            itemEntity.setUnitPrice(orderItem.getUnitPrice());
            itemEntity.setOrder(orderEntity);
            items.add(itemEntity);
        }
        orderEntity.setOrderItems(items != null ? items : new HashSet<>());

        return orderEntity;
    }


    public Order mapFromEntity(OrderEntity entity) {
        return Order.builder()
                .orderNumber(entity.getOrderNumber())
                .orderDate(entity.getOrderDate())
                .customerEmail(entity.getClientEmail())
                .orderItems(
                        entity.getOrderItems()
                                .stream()
                                .map(orderItemEntity -> OrderItem
                                        .builder()
                                        .quantity(orderItemEntity.getQuantity())
                                        .mealId(orderItemEntity.getMealId())
                                        .unitPrice(orderItemEntity.getUnitPrice())
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .restaurantId(entity.getRestaurantId())
                .status(entity.getStatus())
                .totalPrice(entity.getTotalPrice())
                .isCancellable(entity.getOrderDate().plusMinutes(5).isAfter(OffsetDateTime.now()))                .build();
    }

}
