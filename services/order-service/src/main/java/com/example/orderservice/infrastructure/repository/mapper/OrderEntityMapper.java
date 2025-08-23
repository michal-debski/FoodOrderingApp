package com.example.orderservice.infrastructure.repository.mapper;


import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderItem;
import com.example.orderservice.infrastructure.entity.OrderEntity;
import com.example.orderservice.infrastructure.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderEntityMapper {

    public OrderEntity mapToEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setClientEmail(order.getCustomerEmail());
        orderEntity.setRestaurantId(order.getRestaurantId());
        orderEntity.setOrderDate(order.getOrderDate());
        orderEntity.setOrderNumber(order.getOrderNumber());
        orderEntity.setTotalPrice(order.getTotalPrice());
        orderEntity.setStatus(order.getStatus());
        Set<OrderItemEntity> items = order.getOrderItems().stream()
                .map(orderItem -> {
                    OrderItemEntity item = new OrderItemEntity();
                    item.setOrderItemId(String.valueOf(UUID.randomUUID()));
                    item.setMealId(orderItem.getMealId());
                    item.setQuantity(orderItem.getQuantity());
                    item.setUnitPrice(orderItem.getUnitPrice());
                    item.setOrder(
                            OrderEntity.builder()
                                    .restaurantId(order.getRestaurantId())
                                    .orderNumber(order.getOrderNumber())
                                    .build());
                    return item;
                })
                .collect(Collectors.toSet());
        orderEntity.setOrderItems(items);
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
                                        .orderNumber(orderItemEntity.getOrder().getOrderNumber())
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .restaurantId(entity.getRestaurantId())
                .status(entity.getStatus())
                .totalPrice(entity.getTotalPrice())
                .isCancellable(entity.getOrderDate().isBefore(entity.getOrderDate().plusMinutes(10)))
                .build();
    }

}
