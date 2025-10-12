package com.example.orderservice.api.controller;

import com.example.orderservice.api.dto.OrderDTO;
import com.example.orderservice.api.dto.OrderRequestDTO;
import com.example.orderservice.api.dto.mapper.OrderItemMapper;
import com.example.orderservice.api.dto.mapper.OrderMapper;
import com.example.orderservice.business.OrderService;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderItem;
import com.example.orderservice.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @PostMapping("/{restaurantId}/order")
    public ResponseEntity<?> placeOrder(
            @PathVariable String restaurantId,
            @RequestBody OrderRequestDTO orderRequest,
            @RequestHeader("X-User-Email") String email
    ) {
        System.out.println("email: " + email);

        List<OrderItem> orderItemsFromRequest = orderRequest.orderItems()
                .stream()
                .map(orderItemMapper::map)
                .collect(Collectors.toList());

        Order order = orderService.buildOrder(orderItemsFromRequest, restaurantId, email);
        OrderDTO orderDTOToShow = orderMapper
                .mapToDTO(order);

        return ResponseEntity.ok(orderDTOToShow);
    }

    @DeleteMapping("/{orderNumber}")
    public ResponseEntity<?> deleteOrder(
            @PathVariable String orderNumber
    ) {
        orderService.deleteOrder(orderNumber);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/all")
    public ResponseEntity<?> showOrdersForCustomerEmail(
            @RequestHeader("X-User-Email") String email
    ) {
        List<Order> listOfOrders = orderService.findAllOrders();
        List<OrderDTO> list = listOfOrders.stream().map(orderMapper::mapToDTO).toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{orderNumber}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable String orderNumber,
            @RequestBody OrderDTO orderDTO
    ) {
        Order orderByOrderNumber = orderService.findByOrderNumber(orderNumber)
                .orElseThrow(() ->
                        new NotFoundException("Order with order number [%s] does not exist".formatted(orderNumber))
                );
        orderByOrderNumber.setStatus(orderDTO.status());
        orderService.updateOrder(orderByOrderNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{restaurantId}/orders")
    public ResponseEntity<?> showOrdersForRestaurantEmail(
            @PathVariable String restaurantId
    ) {
        List<OrderDTO> ordersByRestaurant = orderService.findOrdersByRestaurantId(restaurantId)
                .stream()
                .map(orderMapper::mapToDTO)
                .toList();
        return ResponseEntity.ok(ordersByRestaurant);
    }
}
