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
        SELECT street.restaurants FROM StreetEntity street
        JOIN street.restaurants r
        JOIN street.name s
        WHERE LOWER(s) LIKE LOWER(CONCAT('%',:street,'%'))
        """)
    List<RestaurantEntity> findAllByStreetName(final @Param("street") String street);

    @Query("""
            SELECT rs FROM RestaurantEntity rs
                where rs.ownerEmail = :ownerEmail
    """)
    List<RestaurantEntity> findRestaurantByOwnerEmail(final @Param("ownerEmail") String ownerEmail);

    Optional<RestaurantEntity> findByRestaurantId(String restaurantId);
}
