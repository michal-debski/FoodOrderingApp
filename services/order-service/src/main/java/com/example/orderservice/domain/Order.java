package com.example.orderservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
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

}
