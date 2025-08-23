package com.example.mealservice.infrastructure.respository;


import com.example.mealservice.domain.Category;
import com.example.mealservice.domain.Meal;
import com.example.mealservice.infrastructure.entity.MealEntity;
import org.springframework.stereotype.Component;

@Component
public class MealEntityMapper {

    MealEntity mapToEntity(Meal meal) {
        return MealEntity.builder()
                .mealId(meal.mealId())
                .price(meal.price())
                .name(meal.name())
                .category(meal.category().getName())
                .description(meal.description())
                .restaurantId(meal.restaurantId())
                .build();
    }



    Meal mapFromEntity(MealEntity mealEntity) {
        return Meal.builder()
                .mealId(mealEntity.getMealId())
                .price(mealEntity.getPrice())
                .name(mealEntity.getName())
                .description(mealEntity.getDescription())
                .category(Category.fromDisplayName(mealEntity.getCategory()))
                .restaurantId(mealEntity.getRestaurantId())
                .build();
    }


}
