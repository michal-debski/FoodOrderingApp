package com.example.mealservice.api.dto;


import com.example.mealservice.domain.Meal;
import com.example.mealservice.domain.MealIngredient;
import com.example.mealservice.infrastructure.entity.Unit;
import org.springframework.stereotype.Component;

import static com.example.mealservice.domain.Category.fromDisplayName;

@Component
public class MealMapper {

    public MealDTO mapToDTO(Meal meal) {
        return MealDTO.builder()
                .mealId(meal.mealId())
                .name(meal.name())
                .description(meal.description())
                .category(meal.category().getName())
                .price(meal.price())
                .ingredientsForMeal(meal.ingredients()
                        .stream()
                        .map(mealIngredient -> IngredientForMealDTO.builder()
                                .unit(mealIngredient.unit().toString())
                                .name(mealIngredient.name())
                                .quantity(mealIngredient.quantity())

                        .build())
                        .toList()
                )
                .build();
    }


    public Meal map(MealDTO mealDTO) {
        return Meal.builder()
                .name(mealDTO.name())
                .description(mealDTO.description())
                .restaurantId(mealDTO.restaurantId())
                .category(fromDisplayName(mealDTO.category()))
                .price(mealDTO.price())
                .ingredients(mealDTO.ingredientsForMeal()
                        .stream()
                        .map(ingredientForMealDTO ->
                                new MealIngredient(
                                        ingredientForMealDTO.name(),
                                        ingredientForMealDTO.quantity(),
                                        Unit.valueOf(ingredientForMealDTO.unit())))
                        .toList()
                )
                .build();
    }

    public Meal mapForSave(MealDTO mealDTO, String restaurantId) {
        return Meal.builder()
                .name(mealDTO.name())
                .description(mealDTO.description())
                .restaurantId(restaurantId)
                .category(fromDisplayName(mealDTO.category()))
                .price(mealDTO.price())
                .ingredients(mealDTO.ingredientsForMeal()
                        .stream()
                        .map(ingredientForMealDTO ->
                                new MealIngredient(
                                        ingredientForMealDTO.name(),
                                        ingredientForMealDTO.quantity(),
                                        Unit.valueOf(ingredientForMealDTO.unit())))
                        .toList()
                )
                .build();
    }

    public MealDTO mapForSaveToDTO(Meal meal) {
        return MealDTO.builder()
                .name(meal.name())
                .description(meal.description())
                .restaurantId(meal.restaurantId())
                .category(fromDisplayName(meal.category().getName()).getName())
                .price(meal.price())
                .ingredientsForMeal(meal.ingredients()
                        .stream()
                        .map(mealIngredient -> IngredientForMealDTO.builder()
                                .unit(mealIngredient.unit().toString())
                                .name(mealIngredient.name())
                                .quantity(mealIngredient.quantity())
                                .build())
                        .toList()
                )
                .build();
    }
}
