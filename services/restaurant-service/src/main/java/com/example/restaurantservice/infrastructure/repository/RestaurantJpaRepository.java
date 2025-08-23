package com.example.restaurantservice.infrastructure.repository;

import com.example.restaurantservice.infrastructure.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity,Integer> {


    Optional<RestaurantEntity> findByRestaurantName(String name);

    @Query("""
        SELECT rs.restaurant FROM RestaurantStreetEntity rs
        JOIN rs.restaurant r
        JOIN rs.street s
        WHERE LOWER(s.name) LIKE LOWER(CONCAT('%',:street,'%'))
        """)
    List<RestaurantEntity> findAllByStreetName(final @Param("street") String street);

}
