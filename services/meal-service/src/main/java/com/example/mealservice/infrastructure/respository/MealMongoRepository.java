package com.example.mealservice.infrastructure.respository;

import com.example.mealservice.infrastructure.entity.MealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MealMongoRepository extends JpaRepository<MealEntity, String> {


    Set<MealEntity> findByCategory(String category);


    List<MealEntity> findAllByRestaurantId(String restaurantId);
}
