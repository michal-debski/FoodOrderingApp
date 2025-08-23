package com.example.orderservice.infrastructure.repository.jpa;

import com.example.orderservice.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Integer> {

    Optional<OrderEntity> findByOrderNumber(@Param("orderNumber") String orderNumber);

    List<OrderEntity> findAll();


    List<OrderEntity> findByClientEmail(String id);

    @Query(
            """
                    SELECT SUM(oi.quantity * oi.unitPrice) FROM OrderItemEntity oi
                    JOIN oi.mealId m
                    JOIN oi.order o
                    WHERE o.orderNumber = :orderNumber
                    """
    )
    BigDecimal getTotalOrderPrice(@Param("orderNumber") String orderNumber);
}
