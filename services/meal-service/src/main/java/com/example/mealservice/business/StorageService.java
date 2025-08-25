package com.example.mealservice.business;

import com.example.mealservice.api.dto.IngredientUpdateRequest;
import com.example.mealservice.domain.Ingredient;
import com.example.mealservice.exception.AlreadyExistsException;
import com.example.mealservice.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class StorageService {

    private final StorageDAO storageDAO;

    @Transactional
    public Ingredient addNewIngredientToStore(Ingredient ingredient, String restaurantId) {
        log.info("Trying to add ingredient to the store");
        Optional<Ingredient> ingredientByName = storageDAO.findIngredientByName(ingredient.name());
        if (ingredientByName.isPresent()) {
            log.info("Ingredient with name {} already exists", ingredient.name());
            throw new AlreadyExistsException("Ingredient already exists in db");
        }
            return storageDAO.addNewIngredientToStore(ingredient, restaurantId);

    }

    @Transactional
    public Ingredient updateIngredientQuantity(IngredientUpdateRequest ingredient) {
        log.info("Trying to update ingredient");
        Optional<Ingredient> ingredientByName = storageDAO.findIngredientByName(ingredient.name());
        if(ingredientByName.isEmpty()) {
            throw new NotFoundException("Ingredient not found");
        }

        Ingredient foundIngredient = ingredientByName.get();
        return storageDAO.updateIngredientQuantityInStorage(new Ingredient(
                foundIngredient.ingredientId(),
                ingredient.name(),
                ingredient.quantity(), ingredient.unitName(),
                foundIngredient.restaurantId()
        ));
    }

    public List<Ingredient> getAllIngredientsForGivenRestaurantId(String restaurantId) {
        return storageDAO.findAllIngredientsByRestaurantId(restaurantId);
    }

    public List<Ingredient> findAllIngredientsByName() {
        return storageDAO.findAll();
    }
}
