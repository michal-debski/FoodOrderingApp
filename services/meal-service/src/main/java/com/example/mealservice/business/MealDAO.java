package com.example.mealservice.business;



import com.example.mealservice.domain.Meal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public interface MealDAO {

    Set<Meal> findAllMealsByCategory(String category);

    List<Meal> findAllMeals();

    Meal saveMeal(Meal meal);

    void deleteById(String id);

    List<Meal> findAllMealsBySelectedRestaurant(String id);

    Optional<Meal> findMealById(String mealId);

    Meal updateMeal(Meal meal);

}
