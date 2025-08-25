package com.example.mealservice.infrastructure.respository;

import com.example.mealservice.domain.Ingredient;
import com.example.mealservice.infrastructure.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageJpaRepository extends JpaRepository<IngredientEntity, String> {
    List<IngredientEntity> findAllByRestaurantId(String restaurantId);

    Optional<Ingredient> findIngredientByName(String ingredientName);
}
