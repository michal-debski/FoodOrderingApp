package com.example.orderservice.infrastructure.repository;

import com.example.orderservice.business.dao.OrderDAO;
import com.example.orderservice.domain.Order;
import com.example.orderservice.infrastructure.entity.OrderEntity;
import com.example.orderservice.infrastructure.repository.jpa.OrderJpaRepository;
import com.example.orderservice.infrastructure.repository.mapper.OrderEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderRepository implements OrderDAO {

    private final OrderEntityMapper orderEntityMapper;

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order saveOrder(Order order) {
        OrderEntity orderEntity = orderEntityMapper.mapToEntity(order);
        System.out.println(orderEntity.getOrderItems());
        OrderEntity save = orderJpaRepository.save(orderEntity);
        System.out.println( save.getOrderItems());
        return orderEntityMapper.mapFromEntity(save);
    }

    @Override
    public void deleteOrder(String orderNumber) {
        orderJpaRepository.findByOrderNumber(orderNumber).ifPresent(orderJpaRepository::delete);
    }

    @Override
    public Optional<Order> findOrderByOrderNumber(String orderNumber) {
        return orderJpaRepository.findByOrderNumber(orderNumber)
                .map(orderEntityMapper::mapFromEntity);
    }

    @Override
    public List<Order> findOrderByClientEmail(String email) {
        return orderJpaRepository.findByClientEmail(email).stream()
                .map(orderEntityMapper::mapFromEntity).toList();
    }

    @Override
    public List<Order> findAll() {
        List<OrderEntity> orderEntityList = orderJpaRepository.findAll();
        return orderEntityList.stream().map(orderEntityMapper::mapFromEntity).toList();
    }

    @Override
    public void updateOrder(Order order) {
        orderJpaRepository.save(orderEntityMapper.mapToEntity(order));
    }

    @Override
    public List<Order> findOrdersByRestaurantId(String restaurantId) {
        return orderJpaRepository.findOrdersByRestaurantId(restaurantId)
                .stream().map(orderEntityMapper::mapFromEntity).toList();
    }


    @Override
    public BigDecimal getTotalOrderPrice(String orderNumber) {
        return orderJpaRepository.getTotalOrderPrice(orderNumber);
    }


}
