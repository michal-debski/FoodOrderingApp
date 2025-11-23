package com.example.mealservice.infrastructure.respository;

import com.example.mealservice.infrastructure.entity.MealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MealJpaRepository extends JpaRepository<MealEntity, String> {

    Set<MealEntity> findByCategory(String category);

    Optional<MealEntity> findByName(String name);

    List<MealEntity> findAllByRestaurantId(String restaurantId);

    List<MealEntity> findAllByMealIdIn(List<String> ids);
}
