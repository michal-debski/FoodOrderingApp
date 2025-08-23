package com.example.orderservice.business;

import com.example.orderservice.business.dao.OrderItemDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderItemService {

    private final OrderItemDAO orderItemDAO;

    @Transactional
    public void deleteOrderItemsByOrderOrderNumber(String orderNumber) {
        orderItemDAO.deleteOrderItemsByOrderNumber(orderNumber);
        log.info("Deleted order items by order number: {}", orderNumber);
    }

}
