package com.example.mealservice.business;

import com.example.mealservice.domain.Ingredient;
import com.example.mealservice.domain.Meal;
import com.example.mealservice.api.dto.MealUpdateRequest;
import com.example.mealservice.domain.MealIngredient;

import com.example.mealservice.exception.NotFoundException;
import com.example.mealservice.grpc.OrderItem;
import com.example.mealservice.infrastructure.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
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
                "Start making meal for restaurant: [{}]",
                meal.restaurantId()
        );

        List<Ingredient> allIngredientsByRestaurantId = storageDAO.findAllIngredientsByRestaurantId(meal.restaurantId());
        List<String> ingredientsName = allIngredientsByRestaurantId.stream().map(Ingredient::name).toList();
        List<String> ingredients = meal.ingredients()
                .stream()
                .map(MealIngredient::name).toList();
        addIngredientToStorage(meal, ingredients, ingredientsName);
        log.info(
                "Making meal and added ingredients to the storage for restaurant successfully ended: [{}]",
                meal.restaurantId()
        );
        return mealDAO.saveMeal(meal);
    }

    public void deleteMeal(String mealName) {
        log.info(
                "Trying to delete meal with name: [{}]",
                mealName
        );
        mealDAO.deleteByMealName(mealName);
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
        Optional<Meal> optionalMeal = mealDAO.findMealById(mealId);
        if (optionalMeal.isPresent()) {
            return mealDAO.updateMeal(Meal.builder()
                    .mealId(optionalMeal.get().mealId())
                    .category(optionalMeal.get().category())
                    .name(mealUpdateRequest.name())
                    .description(mealUpdateRequest.description())
                    .price(mealUpdateRequest.price())
                    .ingredients(
                            mealUpdateRequest.ingredientsForMeal()
                                    .stream()
                                    .map(ingredientForMealDTO ->
                                            new MealIngredient(
                                                    ingredientForMealDTO.name(),
                                                    ingredientForMealDTO.quantity(),
                                                    Unit.valueOf(ingredientForMealDTO.unit())
                                            )
                                    )
                                    .toList()
                    )
                    .restaurantId(optionalMeal.get().restaurantId())
                    .build());
        } else {
            throw new NotFoundException("Meal not found");
        }

    }


    @Transactional
    public List<Meal> findMealsWhichCannotBePrepared(List<OrderItem> orderItems) {
        log.info("Finding meals which cannot be prepared. From order items: {}", orderItems);
        HashMap<String, Integer> requiredIngredients = new HashMap<>();
        List<Meal> meals = orderItems.stream()
                .map((OrderItem orderItem) -> mealDAO.findMealById(orderItem.getMealId()))
                .flatMap(Optional::stream)
                .toList();

        if (meals.isEmpty()) {
            log.info("No meals found from the provided list.");
            throw new NotFoundException("No meals from list have been found");
        }

        for (OrderItem orderItem : orderItems) {
            Meal meal = mealDAO.findMealById(orderItem.getMealId())
                    .orElseThrow(() -> new NotFoundException("Meal not found"));

            for (MealIngredient ingredient : meal.ingredients()) {
                String name = ingredient.name();
                int totalQuantity = ingredient.quantity() * orderItem.getQuantity();

                requiredIngredients.merge(name, totalQuantity, Integer::sum);
            }
        }

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

    private void addIngredientToStorage(Meal meal, List<String> ingredients, List<String> ingredientsName) {
        log.info("Start adding new ingredients: [{}]", ingredients);
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
                                    .quantity(0)
                                    .build(), meal.restaurantId());
                }
            }
        }
    }
}
