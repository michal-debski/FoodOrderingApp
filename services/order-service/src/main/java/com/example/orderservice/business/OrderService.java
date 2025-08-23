package com.example.orderservice.business;

import com.example.orderservice.business.dao.OrderDAO;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderItem;
import com.example.orderservice.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final OrderDAO orderDAO;

    private final OrderItemService orderItemService;

    public void deleteOrder(String orderNumber) {
        orderDAO.findOrderByOrderNumber(orderNumber).ifPresent(order -> {
            if (order.isCancellable()){
                orderItemService.deleteOrderItemsByOrderOrderNumber(orderNumber);
                orderDAO.deleteOrder(orderNumber);
            } else {
                log.warn("You cannot delete order, because order has been ordered more than 10 minutes ago.");
            }
        });

    }

    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderDAO.findOrderByOrderNumber(orderNumber);
    }

    @Transactional
    public List<Order> findOrderByClientEmail(String email) {
        return orderDAO.findOrderByClientEmail(email);
    }

    public Order buildOrder(List<OrderItem> orderItems, String restaurantId, String email) {
        OffsetDateTime dateOfOrder = OffsetDateTime.now();
        System.out.println(orderItems);
        Order orderPlaced = Order.builder()
                .orderNumber(generateOrderNumber())
                .status("Preparing by chef")
                .orderDate(dateOfOrder)
                .customerEmail(email)
                .restaurantId(restaurantId)
                .build();
        orderPlaced.setOrderItems(orderItems);
        System.out.println(orderPlaced.getOrderItems());
        log.info("Creating order before saving order: {}", orderPlaced);
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        if (!orderItems.isEmpty()) {
            for (OrderItem orderItem : orderItems) {
                BigDecimal multiply = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                totalPrice = totalPrice.add(multiply);
            }
        } else {
            orderDAO.deleteOrder(orderPlaced.getOrderNumber());
            throw new NotFoundException("You didn't choose any meals to your order");
        }
        orderPlaced.setTotalPrice(totalPrice.setScale(2, RoundingMode.HALF_EVEN));
        System.out.println(orderPlaced.getOrderItems());
        System.out.println("Order Placed: " + orderPlaced + " !");
        return orderDAO.saveOrder(orderPlaced);

    }

    public List<Order> findAllOrders() {
        return orderDAO.findAll();
    }

    @Transactional
    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }

    private String generateOrderNumber() {
        return "%s%s%s%s-%s%s".formatted(
                randomChar('A', 'Z'),
                randomChar('A', 'Z'),
                randomChar('A', 'Z'),
                randomChar('A', 'Z'),
                randomInt(10, 100),
                randomInt(10, 100)
        );
    }

    @SuppressWarnings("SameParameterValue")
    private int randomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    @SuppressWarnings("SameParameterValue")
    private int randomChar(char min, char max) {
        return (char) new Random().nextInt(max - min) + min;
    }

}
