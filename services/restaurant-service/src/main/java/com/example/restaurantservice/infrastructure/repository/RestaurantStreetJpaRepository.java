package com.example.restaurantservice.infrastructure.repository;

import com.example.restaurantservice.infrastructure.RestaurantStreetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantStreetJpaRepository extends JpaRepository<RestaurantStreetEntity, Integer> {

    @Query("""
            SELECT str FROM RestaurantStreetEntity str
            Inner JOIN FETCH str.restaurant rest
            WHERE rest.restaurantId=:id
            """)
    List<RestaurantStreetEntity> findAllByRestaurantId(@Param("id") Integer id);
}
