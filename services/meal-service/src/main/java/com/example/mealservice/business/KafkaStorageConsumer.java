package com.example.mealservice.business;

import com.example.mealservice.domain.Ingredient;
import com.example.mealservice.domain.IngredientRemovalFromStorageMessage;
import com.example.mealservice.api.dto.IngredientUpdateRequest;
import com.example.mealservice.domain.Meal;
import com.example.mealservice.domain.MealIngredient;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    void getIngredientRemovalFromStorageMessage(IngredientRemovalFromStorageMessage ingredientRemovalFromStorageMessage) {
        System.out.println("Received: " + ingredientRemovalFromStorageMessage);
        List<Ingredient> matchingIngredient = new ArrayList<>();

        List<MealIngredient> allIngredients = ingredientRemovalFromStorageMessage.orderItems().stream()
                .flatMap(t -> mealMenuService.findMealById(t.getMealId())
                        .stream()
                )
                .toList()
                .stream()
                .flatMap(meal -> meal.ingredients().stream())
                .toList();

        List<String> allIngredientsName = allIngredients.stream()
                .map(MealIngredient::name)
                .toList();

        List<Ingredient> foundIngredient = storageService.findAllIngredientsByName();
        for (Ingredient ingredient : foundIngredient) {
            if(allIngredientsName.contains(ingredient.name())) {
                matchingIngredient.add(ingredient);
            }
        }
        for (Ingredient ingredient : matchingIngredient) {
            foundIngredient.stream().filter(found -> ingredient.name().equals(found.name())).map(found -> new IngredientUpdateRequest(
                    ingredient.name(),
                    found.quantity() - ingredient.quantity(),
                    ingredient.unitName()
            )).forEachOrdered(storageService::updateIngredientQuantity);
        }

    }
}
