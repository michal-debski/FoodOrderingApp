package com.example.orderservice.infrastructure.repository;

import com.example.orderservice.business.dao.OrderItemDAO;
import com.example.orderservice.infrastructure.repository.jpa.OrderItemJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OrderItemRepository implements OrderItemDAO {

    private final OrderItemJpaRepository orderItemJpaRepository;

    @Override
    public void deleteOrderItemsByOrderNumber(String orderNumber) {
        orderItemJpaRepository.deleteOrderItemsByOrderNumber(orderNumber);
    }
}
