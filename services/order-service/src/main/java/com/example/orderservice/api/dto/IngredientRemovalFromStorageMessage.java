package com.example.orderservice.api.dto;

import com.example.orderservice.domain.OrderItem;
import lombok.Builder;

import java.util.List;

@Builder
public record IngredientRemovalFromStorageMessage(
        String orderNumber,
        List<OrderItem> orderItems
){

}
