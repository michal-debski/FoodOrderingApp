package com.example.orderservice.api.dto;

public record OrderUpdateStatusRequest(
        String orderNumber,
        String status
) {
}
