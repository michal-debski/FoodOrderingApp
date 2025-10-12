package com.example.mealservice.business;

import com.example.mealservice.domain.Ingredient;
import com.example.mealservice.domain.IngredientChangeStateInStorageMessage;
import com.example.mealservice.api.dto.IngredientUpdateRequest;
import com.example.mealservice.domain.MealIngredient;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class KafkaStorageConsumer {

    private final StorageService storageService;
    private final MealMenuService mealMenuService;

    @KafkaListener(
            topics = "storage_remove",
            groupId = "groupId",
            containerFactory = "factory"
    )
    void getIngredientRemovalFromStorageMessage(IngredientChangeStateInStorageMessage ingredientChangeStateInStorageMessage) {
        System.out.println("Received: " + ingredientChangeStateInStorageMessage);

        List<MealIngredient> allIngredientsFromMealsIncludedInMessage = ingredientChangeStateInStorageMessage.orderItems()
                .stream()
                .flatMap(orderItem -> mealMenuService.findMealById(orderItem.getMealId())
                        .stream()
                )
                .toList()
                .stream()
                .flatMap(meal -> meal.ingredients().stream())
                .toList();

        List<Ingredient> matchingIngredient = mapToIngredientList(allIngredientsFromMealsIncludedInMessage);

        changeQuantityOfIngredientInStorage(matchingIngredient, allIngredientsFromMealsIncludedInMessage);

    }

    @KafkaListener(
            topics = "storage_change_state",
            groupId = "groupId",
            containerFactory = "factory"
    )
    void getIngredientChangeStateInStorageWhenOrderCancellation(
            IngredientChangeStateInStorageMessage ingredientChangeStateInStorageMessage
    ) {
        System.out.println("Received: " + ingredientChangeStateInStorageMessage + ", when order has been cancelled");

        List<MealIngredient> allIngredientsFromMealsIncludedInMessage = ingredientChangeStateInStorageMessage.orderItems()
                .stream()
                .flatMap(orderItem -> mealMenuService.findMealById(orderItem.getMealId())
                        .stream()
                )
                .toList()
                .stream()
                .flatMap(meal -> meal.ingredients().stream())
                .toList();

        List<Ingredient> matchingIngredient = mapToIngredientList(allIngredientsFromMealsIncludedInMessage);

        changeQuantityOfIngredientInStorageWhenOrderHasBeenCancelled(
                matchingIngredient,
                allIngredientsFromMealsIncludedInMessage
        );

    }

    private List<Ingredient> mapToIngredientList(List<MealIngredient> allIngredientsFromMealsIncludedInMessage) {
        List<Ingredient> matchingIngredient = new ArrayList<>();


        List<String> allIngredientsFromMealsIncludedInMessageNames = allIngredientsFromMealsIncludedInMessage.stream()
                .map(MealIngredient::name)
                .toList();

        List<Ingredient> foundIngredients = storageService.findAllIngredientsByName();
        for (Ingredient ingredient : foundIngredients) {
            if(allIngredientsFromMealsIncludedInMessageNames.contains(ingredient.name())) {
                matchingIngredient.add(ingredient);
            }
        }
        return matchingIngredient;
    }

    private void changeQuantityOfIngredientInStorage(
            List<Ingredient> matchingIngredient,
            List<MealIngredient> allIngredientsFromMealsIncludedInMessage
    ) {
        for (Ingredient ingredient : matchingIngredient) {
            allIngredientsFromMealsIncludedInMessage.stream()
                    .filter(found -> ingredient.name().equals(found.name()))
                    .map(found -> {
                        return new IngredientUpdateRequest(
                                ingredient.name(),
                                ingredient.quantity() - found.quantity(),
                                ingredient.unitName()
                                );
                    }

                    ).forEachOrdered(storageService::increaseIngredientQuantity);
        }
    }

    private void changeQuantityOfIngredientInStorageWhenOrderHasBeenCancelled(
            List<Ingredient> matchingIngredient,
            List<MealIngredient> allIngredientsFromMealsIncludedInMessage
    ) {
        for (Ingredient ingredient : matchingIngredient) {
            allIngredientsFromMealsIncludedInMessage.stream()
                    .filter(found -> ingredient.name().equals(found.name()))
                    .map(found -> {
                                return new IngredientUpdateRequest(
                                        ingredient.name(),
                                        ingredient.quantity() + found.quantity(),
                                        ingredient.unitName()
                                );
                            }

                    ).forEachOrdered(storageService::increaseIngredientQuantity);
        }
    }
}
