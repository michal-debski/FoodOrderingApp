package com.example.mealservice.infrastructure.respository;

import com.example.mealservice.domain.Ingredient;
import com.example.mealservice.infrastructure.entity.IngredientEntity;
import org.springframework.stereotype.Component;

@Component
public class StorageEntityMapper {

    public IngredientEntity mapToEntity(Ingredient ingredient) {
        return new IngredientEntity(
                ingredient.ingredientId(),
                ingredient.name(),
                ingredient.quantity(),
                ingredient.unitName(),
                ingredient.restaurantId()
        );
    }
    public Ingredient mapToDomain(IngredientEntity ingredientEntity) {
        return new Ingredient(
                ingredientEntity.getIngredientId(),
                ingredientEntity.getName(),
                ingredientEntity.getQuantity(),
                ingredientEntity.getUnitName(),
                ingredientEntity.getRestaurantId()
        );
    }
}
