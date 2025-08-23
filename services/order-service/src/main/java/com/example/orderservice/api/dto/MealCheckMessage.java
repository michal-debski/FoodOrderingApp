package com.example.orderservice.api.dto;

import com.example.orderservice.domain.OrderItem;
import lombok.Builder;

import java.util.List;

@Builder
public record MealCheckMessage (String orderNumber, List<OrderItem> orderItems){


}
