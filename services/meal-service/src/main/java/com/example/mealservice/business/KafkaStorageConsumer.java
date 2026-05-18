package com.example.mealservice.business;

import com.example.mealservice.domain.Ingredient;
import com.example.mealservice.domain.IngredientChangeStateInStorageMessage;
import com.example.mealservice.api.dto.IngredientUpdateRequest;
import com.example.mealservice.domain.MealIngredient;
import com.example.mealservice.domain.OrderItem;
import com.example.mealservice.infrastructure.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        log.info("Received order for ingredient removal: {}", ingredientChangeStateInStorageMessage);
        List<MealIngredient> allNeededIngredients = new ArrayList<>();

        for (OrderItem item : ingredientChangeStateInStorageMessage.orderItems()) {
            mealMenuService.findMealById(item.getMealId()).ifPresent(meal -> {
                log.info("Processing meal: {} (mealId: {}), quantity: {}", meal.name(), item.getMealId(), item.getQuantity());
                for (MealIngredient mi : meal.ingredients()) {
                    int totalBaseQuantity = mi.quantity() * mi.unit().getFactor() * item.getQuantity();
                    log.debug("Ingredient: {}, Original: {} {}, Factor: {}, Total in GR: {}", 
                            mi.name(), mi.quantity(), mi.unit(), mi.unit().getFactor(), totalBaseQuantity);

                    allNeededIngredients.add(new MealIngredient(
                            mi.name(),
                            totalBaseQuantity,
                            mi.unit()
                    ));
                }
            });
        }

        log.info("Total ingredients needed: {}", allNeededIngredients);
        List<Ingredient> matchingIngredient = mapToIngredientList(allNeededIngredients);
        log.info("Matching ingredients from storage: {}", matchingIngredient);
        changeQuantityOfIngredientInStorage(matchingIngredient, allNeededIngredients);
    }

    @KafkaListener(
            topics = "storage_change_state",
            groupId = "groupId",
            containerFactory = "factory"
    )
    void getIngredientChangeStateInStorageWhenOrderCancellation(
            IngredientChangeStateInStorageMessage ingredientChangeStateInStorageMessage
    ) {
        log.info("Received order cancellation for ingredient return: {}", ingredientChangeStateInStorageMessage);

        List<MealIngredient> allIngredientsFromMealsIncludedInMessage = ingredientChangeStateInStorageMessage.orderItems()
                .stream()
                .flatMap(orderItem -> {
                    var mealOptional = mealMenuService.findMealById(orderItem.getMealId());
                    if (mealOptional.isEmpty()) {
                        log.warn("Meal not found for mealId: {}", orderItem.getMealId());
                    } else {
                        log.info("Processing cancellation for meal: {} (mealId: {})", 
                                mealOptional.get().name(), orderItem.getMealId());
                    }
                    return mealOptional.stream();
                })
                .toList()
                .stream()
                .flatMap(meal -> meal.ingredients().stream())
                .toList();

        log.info("Total ingredients to return: {}", allIngredientsFromMealsIncludedInMessage);
        List<Ingredient> matchingIngredient = mapToIngredientList(allIngredientsFromMealsIncludedInMessage);
        log.info("Matching ingredients from storage: {}", matchingIngredient);

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
        log.info("Starting ingredient removal from storage");
        for (Ingredient storageIng : matchingIngredient) {
            int totalNeededInGrams = allIngredientsFromMealsIncludedInMessage.stream()
                    .filter(found -> storageIng.name().equals(found.name()))
                    .mapToInt(found -> found.quantity() * found.unit().getFactor())
                    .sum();

            int currentStockInGrams = storageIng.quantity() * Unit.valueOf(storageIng.unitName()).getFactor();
            int newQuantityInGrams = currentStockInGrams - totalNeededInGrams;

            log.info("Ingredient: {}", storageIng.name());
            log.info("  Current storage: {} {} (= {} GR)", storageIng.quantity(), storageIng.unitName(), currentStockInGrams);
            log.info("  Needed: {} GR", totalNeededInGrams);
            log.info("  New quantity: {} GR", newQuantityInGrams);

            storageService.increaseIngredientQuantity(new IngredientUpdateRequest(
                    storageIng.name(),
                    newQuantityInGrams,
                    "GR"
            ));
            log.info("  Saved to database: {} = {} GR", storageIng.name(), newQuantityInGrams);
        }
    }

    private void changeQuantityOfIngredientInStorageWhenOrderHasBeenCancelled(
            List<Ingredient> matchingIngredient,
            List<MealIngredient> allIngredientsFromMealsIncludedInMessage
    ) {
        log.info("Starting ingredient return to storage (order cancelled)");
        for (Ingredient ingredient : matchingIngredient) {
            int totalReturnedInGrams = allIngredientsFromMealsIncludedInMessage.stream()
                    .filter(found -> ingredient.name().equals(found.name()))
                    .mapToInt(found -> found.quantity() * found.unit().getFactor())
                    .sum();

            int currentStockInGrams = ingredient.quantity() * Unit.valueOf(ingredient.unitName()).getFactor();
            int newQuantityInGrams = currentStockInGrams + totalReturnedInGrams;

            log.info("Ingredient: {}", ingredient.name());
            log.info("  Current storage: {} {} (= {} GR)", ingredient.quantity(), ingredient.unitName(), currentStockInGrams);
            log.info("  Returning: {} GR", totalReturnedInGrams);
            log.info("  New quantity: {} GR", newQuantityInGrams);

            storageService.increaseIngredientQuantity(new IngredientUpdateRequest(
                    ingredient.name(),
                    newQuantityInGrams,
                    "GR"
            ));
            log.info("  Saved to database: {} = {} GR", ingredient.name(), newQuantityInGrams);
        }
    }
}
