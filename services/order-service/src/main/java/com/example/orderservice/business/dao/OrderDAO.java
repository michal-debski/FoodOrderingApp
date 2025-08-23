package com.example.orderservice.business.dao;


import com.example.orderservice.domain.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    Order saveOrder(Order order);

    void deleteOrder(String orderNumber);

    Optional<Order> findOrderByOrderNumber(String orderNumber);

    List<Order> findOrderByClientEmail(String id);


    BigDecimal getTotalOrderPrice(String orderNumber);

    List<Order> findAll();

    void updateOrder(Order order);
}
