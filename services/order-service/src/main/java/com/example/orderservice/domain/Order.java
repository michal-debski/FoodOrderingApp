package com.example.orderservice.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;


@Builder
@Getter
@Setter
@ToString
public class Order {
    public String orderNumber;
    public BigDecimal totalPrice;
    public String status;
    public OffsetDateTime orderDate;
    public String customerEmail;
    public String restaurantId;
    public List<OrderItem> orderItems;
    public boolean isCancellable;

    public boolean isCancellable(){
        return LocalDateTime.now().isBefore(orderDate.toLocalDateTime().plusMinutes(10));
    }


}
