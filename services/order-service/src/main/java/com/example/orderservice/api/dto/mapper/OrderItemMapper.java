package com.example.orderservice.api.dto.mapper;

import com.example.orderservice.api.dto.OrderItemDTO;
import com.example.orderservice.domain.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItem map(OrderItemDTO orderItemDTO) {
        return OrderItem.builder()
                .quantity(orderItemDTO.quantity())
                .mealId(orderItemDTO.mealId())
                .orderNumber(orderItemDTO.orderNumber())
                .unitPrice(orderItemDTO.unitPrice())
                .build();
    }
}
