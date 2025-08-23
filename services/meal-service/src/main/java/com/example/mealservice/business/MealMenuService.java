package com.example.mealservice.business;

import com.example.mealservice.domain.Meal;
import com.example.mealservice.api.dto.MealUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MealMenuService {

    private final MealDAO mealDAO;

    public List<Meal> findAllMeals() {
        List<Meal> meals = mealDAO.findAllMeals();
        log.info(
                "Available meals: [{}]",
                meals
        );
        return meals;
    }

    public List<Meal> findAllBySelectedRestaurant(String id) {
        List<Meal> selectedMealsByRestaurant = mealDAO.findAllMealsBySelectedRestaurant(id);
        log.info(
                "Available meals for restaurant: [{}]",
                selectedMealsByRestaurant
        );
        return selectedMealsByRestaurant;
    }

    @Transactional
    public Meal makeMealForRestaurant(Meal meal) {
        log.info(
                "Making meal for restaurant: [{}]",
                meal.restaurantId()
        );


        return mealDAO.saveMeal(meal);
    }

    public void deleteMeal(String id) {
        log.info(
                "Trying to delete meal with id: [{}]",
                id
        );
        mealDAO.deleteById(id);
    }

    public Optional<Meal> findMealById(String mealId) {
         log.info(
                 "Trying to find meal with id: [{}]",
                 mealId);
        return mealDAO.findMealById(mealId);
    }

    @Transactional
    public Meal updateMeal(MealUpdateRequest mealUpdateRequest, String mealId) {
        log.info(
                "Trying to update meal. Meal update request: [{}]",
                mealUpdateRequest
        );
        Meal mealUpdated = Meal.builder()
                .mealId(mealId)
                .name(mealUpdateRequest.name())
                .description(mealUpdateRequest.description())
                .price(mealUpdateRequest.price())
                .build();
        return mealDAO.findMealById(mealId).isPresent() ? mealDAO.updateMeal(mealUpdated) : null;

    }

    public void checkIfMealsCanBeOrdered(List<String> mealsId) {
        log.info("Checking if meals can be prepared based on substitutes on storage");
        mealsId.stream().map(mealDAO::findMealById).map(t-> t.isPresent()).toList();

    }
}
