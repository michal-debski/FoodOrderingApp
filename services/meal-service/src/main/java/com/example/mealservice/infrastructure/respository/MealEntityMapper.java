package com.example.mealservice.infrastructure.respository;


import com.example.mealservice.domain.Category;
import com.example.mealservice.domain.Meal;
import com.example.mealservice.domain.MealIngredient;
import com.example.mealservice.infrastructure.entity.MealEntity;
import com.example.mealservice.infrastructure.entity.MealIngredientEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MealEntityMapper {

    MealEntity mapToEntity(Meal meal) {
        MealEntity mealEntity = MealEntity.builder()
                .mealId(meal.mealId())
                .price(meal.price())
                .name(meal.name())
                .category(meal.category().getName())
                .description(meal.description())
                .restaurantId(meal.restaurantId())
                .build();

        List<MealIngredientEntity> ingredients = meal.ingredients().stream()
                .map(ingredient -> {
                    MealIngredientEntity entity = new MealIngredientEntity();
                    entity.setName(ingredient.name());
                    entity.setQuantity(ingredient.quantity());
                    entity.setUnit(ingredient.unit());
                    entity.setMeal(mealEntity);
                    return entity;
                })
                .toList();

        mealEntity.setIngredients(ingredients);
        return mealEntity;
    }

    public Meal mapFromEntity(MealEntity mealEntity) {
        List<MealIngredient> ingredients = mealEntity.getIngredients().stream()
                .map(ingredientEntity -> new MealIngredient(
                        ingredientEntity.getName(),
                        ingredientEntity.getQuantity(),
                        ingredientEntity.getUnit()
                ))
                .toList();

        return Meal.builder()
                .mealId(mealEntity.getMealId())
                .price(mealEntity.getPrice())
                .name(mealEntity.getName())
                .description(mealEntity.getDescription())
                .category(Category.fromDisplayName(mealEntity.getCategory()))
                .ingredients(ingredients)
                .restaurantId(mealEntity.getRestaurantId())
                .build();
    }

}
