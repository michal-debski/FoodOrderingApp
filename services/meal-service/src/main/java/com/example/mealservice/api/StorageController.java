package com.example.mealservice.api;

import com.example.mealservice.api.dto.IngredientRequest;
import com.example.mealservice.api.dto.IngredientUpdateRequest;
import com.example.mealservice.api.dto.StorageConditionResponse;
import com.example.mealservice.api.dto.StorageMapper;
import com.example.mealservice.business.StorageService;
import com.example.mealservice.domain.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("meals/{restaurantId}/storage")
public class StorageController {

    private final StorageService storageService;
    private final StorageMapper storageMapper;


    @PostMapping
    public ResponseEntity<?> addIngredient(
            @PathVariable("restaurantId") String restaurantId,
            @RequestBody IngredientRequest ingredientRequest
    ) {
        Ingredient ingredient = storageMapper.mapIngredientRequestToDomain(ingredientRequest);
        Ingredient newIngredientToStore = storageService.addNewIngredientToStore(ingredient, restaurantId);
        return ResponseEntity.ok(storageMapper.mapToDto(newIngredientToStore));
    }

    @PutMapping
    public ResponseEntity<?> increaseIngredientQuantity(
            @PathVariable("restaurantId") String restaurantId,
            @RequestBody IngredientUpdateRequest ingredientRequest
    ) {
        Ingredient ingredient = storageService.increaseIngredientQuantity(ingredientRequest);
        return ResponseEntity.ok(storageMapper.mapToDto(ingredient));
    }

    @GetMapping
    public ResponseEntity<?> getAllIngredients( @PathVariable("restaurantId") String restaurantId){
        List<Ingredient> allIngredientsForGivenRestaurantId =
                storageService.getAllIngredientsForGivenRestaurantId(restaurantId);
        StorageConditionResponse storageConditionResponse = storageMapper.mapToStorageCondition(allIngredientsForGivenRestaurantId);
        return ResponseEntity.ok(storageConditionResponse);
    }

}
