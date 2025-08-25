package com.example.mealservice.business;

import com.example.mealservice.domain.Ingredient;
import com.example.mealservice.domain.Meal;
import com.example.mealservice.api.dto.MealUpdateRequest;
import com.example.mealservice.domain.MealIngredient;
import com.example.mealservice.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MealMenuService {

    private final MealDAO mealDAO;

    private final StorageDAO storageDAO;

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

        List<Ingredient> allIngredientsByRestaurantId = storageDAO.findAllIngredientsByRestaurantId(meal.restaurantId());
        List<String> ingredientsName = allIngredientsByRestaurantId.stream().map(Ingredient::name).toList();
        List<String> ingredients = meal.ingredients()
                .stream()
                .map(MealIngredient::name).toList();
        for (String ingredient : ingredients) {
            if(!ingredientsName.contains(ingredient)) {
                Optional<MealIngredient> mealIngredient = meal.ingredients()
                        .stream()
                        .filter(t -> t.name().equals(ingredient))
                        .findFirst();
                if(mealIngredient.isPresent()) {
                    MealIngredient futureIngredient = mealIngredient.get();
                    storageDAO.addNewIngredientToStore(
                            Ingredient.builder()
                                    .name(futureIngredient.name())
                                    .unitName(String.valueOf(futureIngredient.unit()))
                                    .quantity(futureIngredient.quantity())
                                    .build(), meal.restaurantId());
                }
            }
        }
        log.info(
                "Making meal for restaurant successfully ended: [{}]",
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


    @Transactional
    public List<Meal> findMealsWhichCannotBePrepared(List<String> mealsId) {
        log.info("Finding meals which cannot be prepared. MealsId: {}", mealsId);

        List<Meal> meals = mealsId.stream()
                .map(mealDAO::findMealById)
                .flatMap(Optional::stream)
                .toList();

        if (meals.isEmpty()) {
            log.info("No meals found from the provided list.");
            throw new NotFoundException("No meals from list have been found");
        }

        Map<String, Integer> requiredIngredients = meals.stream()
                .flatMap(meal -> meal.ingredients().stream())
                .collect(Collectors.toMap(
                        MealIngredient::name,
                        MealIngredient::quantity,
                        Integer::sum
                ));
        log.info("Current required ingredients: {}", requiredIngredients);

        Map<String, Integer> storageMap = storageDAO.getStorageMap();
        log.info("Current storage map: {}", storageMap);

        Set<String> insufficientIngredientIds = requiredIngredients.entrySet().stream()
                .filter(entry -> {
                    int required = entry.getValue();
                    int available = storageMap.get(entry.getKey());
                    log.info("Ingredient: {}, Required: {}, Available: {}", entry.getKey(), required, available);
                    return required > available;
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        log.info("Insufficient ingredients: {}", insufficientIngredientIds);

        List<Meal> unavailableMeals = meals.stream()
                .filter(meal -> meal.ingredients().stream()
                        .anyMatch(ingredient -> insufficientIngredientIds.contains(ingredient.name())))
                .toList();
        log.info("Meals which cannot be prepared: {}", unavailableMeals);
        return unavailableMeals;
    }
}
