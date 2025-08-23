package com.example.mealservice.api.dto;


import com.example.mealservice.domain.Meal;
import org.springframework.stereotype.Component;

import static com.example.mealservice.domain.Category.fromDisplayName;

@Component
public class MealMapper {


    public MealDTO mapToDTO(Meal meal) {
        return MealDTO.builder()
                .name(meal.name())
                .description(meal.description())
                .category(meal.category().getName())
                .price(meal.price())
                .build();
    }


    public Meal map(MealDTO mealDTO) {
        return Meal.builder()
                .name(mealDTO.name())
                .description(mealDTO.description())
                .restaurantId(mealDTO.restaurantId())
                .category(fromDisplayName(mealDTO.category()))
                .price(mealDTO.price())
                .build();
    }

    public Meal mapForSave(MealDTO mealDTO, String restaurantId) {
        return Meal.builder()
                .name(mealDTO.name())
                .description(mealDTO.description())
                .restaurantId(restaurantId)
                .category(fromDisplayName(mealDTO.category()))
                .price(mealDTO.price())
                .build();
    }

    public MealDTO mapForSaveToDTO(Meal meal) {
        return MealDTO.builder()
                .name(meal.name())
                .description(meal.description())
                .restaurantId(meal.restaurantId())
                .category(fromDisplayName(meal.category().getName()).getName())
                .price(meal.price())
                .build();
    }
}
