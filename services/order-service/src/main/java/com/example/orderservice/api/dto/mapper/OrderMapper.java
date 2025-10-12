package com.example.orderservice.api.dto.mapper;

import com.example.orderservice.api.dto.OrderDTO;
import com.example.orderservice.api.dto.OrderItemDTO;
import com.example.orderservice.api.dto.IngredientChangeStateInStorageMessage;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper extends OffsetDateTimeMapper {
    public OrderDTO mapToDTO(Order order) {

        return OrderDTO.builder()
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .orderDate(mapOffsetDateTimeToString(order.getOrderDate()))
                .customerEmail(order.customerEmail)
                .orderItems(order.getOrderItems()
                        .stream()
                        .map(orderItem -> OrderItemDTO.builder()
                                .quantity(orderItem.getQuantity())
                                .unitPrice(orderItem.getUnitPrice())
                                .mealId(orderItem.getMealId())
                                .build())
                        .collect(Collectors.toList()))
                .isCancellable(order.isCancellable())
                .build();
    }

    public Order map(OrderDTO order, String email, String restaurantId) {
        return Order.builder()
                .restaurantId(restaurantId)
                .customerEmail(email)
                .orderNumber(order.orderNumber())
                .status(order.status())
                .totalPrice(order.totalPrice())
                .orderDate(mapStringToOffsetDateTime(order.orderDate()))
                .orderItems(order.orderItems()
                        .stream()
                        .map(orderItem -> OrderItem.builder()
                                .quantity(orderItem.quantity())
                                .unitPrice(orderItem.unitPrice())
                                .mealId(orderItem.mealId())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }


    public IngredientChangeStateInStorageMessage mapToIngredientChangeStateInStorageMessage(Order order) {
        return IngredientChangeStateInStorageMessage.builder()
                .orderNumber(order.getOrderNumber())
                .orderItems(order.getOrderItems()
                        .stream()
                        .map(orderItem -> OrderItem.builder()
                                .quantity(orderItem.getQuantity())
                                .unitPrice(orderItem.getUnitPrice())
                                .mealId(orderItem.getMealId())
                                .build())
                        .toList()
                )
                .build();
    }
}
