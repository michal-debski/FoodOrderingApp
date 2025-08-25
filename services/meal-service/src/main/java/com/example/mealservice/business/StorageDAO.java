package com.example.mealservice.business;

import com.example.mealservice.api.dto.IngredientUpdateRequest;
import com.example.mealservice.domain.Ingredient;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public interface StorageDAO {

    List<Ingredient> findAllIngredientsByRestaurantId(String restaurantId);

    Ingredient addNewIngredientToStore(Ingredient ingredient, String restaurantId);

    Ingredient updateIngredientQuantityInStorage(Ingredient ingredient);

    Optional<Ingredient> findIngredientByName(String ingredientName);

    Map<String, Integer> getStorageMap();

    List<Ingredient> findAll();
}
