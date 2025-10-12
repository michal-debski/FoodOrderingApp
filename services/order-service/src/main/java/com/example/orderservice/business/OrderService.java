package com.example.orderservice.business;

import com.example.mealservice.grpc.MealCheckResponse;
import com.example.mealservice.grpc.UnavailableMeal;
import com.example.orderservice.business.dao.OrderDAO;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderItem;
import com.example.orderservice.exception.NotFoundException;
import com.example.orderservice.exception.UnavailableMealsException;
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

    private final MealServiceGrpcClient mealServiceGrpcClient;

    private final KafkaMessageProducerService kafkaMessageProducerService;

    @Transactional
    public void deleteOrder(String orderNumber) {
        orderDAO.findOrderByOrderNumber(orderNumber).ifPresent(order -> {
            if (order.isCancellable()){
                log.info("Order can be cancelled. Deleting order {} from cancellable", orderNumber);
                orderDAO.deleteOrder(orderNumber);
                log.info("Order {} has been deleted successfully!", orderNumber);
                kafkaMessageProducerService.sendMessageToRestoreIngredients(order);
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

    @Transactional
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
        MealCheckResponse mealCheckResponse = mealServiceGrpcClient.checkMealAvailability(orderPlaced.getOrderNumber(), orderItems);
        List<UnavailableMeal> unavailableMeals = mealCheckResponse.getUnavailableMealsList();

        if (!unavailableMeals.isEmpty() || !mealCheckResponse.getCanPrepare()) {
            throw new UnavailableMealsException(unavailableMeals);
        }

        orderPlaced.setOrderItems(orderItems);
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
        Order savedOrder = orderDAO.saveOrder(orderPlaced);
        System.out.println("Order Placed: " + orderPlaced + " !");
        kafkaMessageProducerService.sendMessage(savedOrder);
        return savedOrder;

}

    public List<Order> findAllOrders() {
        return orderDAO.findAll();
    }

    @Transactional
    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }

    public List<Order> findOrdersByRestaurantId(String restaurantId) {
        return orderDAO.findOrdersByRestaurantId(restaurantId);
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
