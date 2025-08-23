package com.example.restaurantservice.infrastructure.repository;

import com.example.restaurantservice.infrastructure.StreetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetJpaRepository extends JpaRepository<StreetEntity, Integer> {
}
