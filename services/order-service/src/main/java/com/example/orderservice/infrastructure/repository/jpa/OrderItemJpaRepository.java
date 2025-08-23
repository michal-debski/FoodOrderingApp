package com.example.orderservice.infrastructure.repository.jpa;

import com.example.orderservice.infrastructure.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderItemEntity oi WHERE oi.order.orderNumber = :orderNumber")
    void deleteOrderItemsByOrderNumber(@Param("orderNumber") String orderNumber);
}
