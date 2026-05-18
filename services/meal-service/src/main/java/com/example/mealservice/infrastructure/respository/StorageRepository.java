package com.example.mealservice.infrastructure.respository;

import com.example.mealservice.business.StorageDAO;
import com.example.mealservice.domain.Ingredient;
import com.example.mealservice.infrastructure.entity.IngredientEntity;
import com.example.mealservice.infrastructure.entity.Unit;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@AllArgsConstructor
public class StorageRepository implements StorageDAO {

    private final StorageJpaRepository storageJpaRepository;
    private final StorageEntityMapper storageEntityMapper;

    @Override
    public List<Ingredient> findAllIngredientsByRestaurantId(String restaurantId) {
        List<IngredientEntity> availableIngredients = storageJpaRepository.findAllByRestaurantId(restaurantId);
        return availableIngredients.stream()
                .map(storageEntityMapper::mapToDomain)
                .toList();
    }


    @Override
    public Ingredient addNewIngredientToStore(Ingredient ingredient) {
        IngredientEntity ingredientEntity = storageEntityMapper.mapToEntity(ingredient);
        IngredientEntity savedIngredient = storageJpaRepository.save(ingredientEntity);
        log.info("Ingredient " + savedIngredient.getName() + " for restaurant " + savedIngredient.getRestaurantId() + "added to database");
        return storageEntityMapper.mapToDomain(savedIngredient);
    }

    @Override
    public Ingredient updateIngredientQuantityInStorage(Ingredient ingredient) {
        IngredientEntity saved = storageJpaRepository.save(
                storageEntityMapper.mapToEntity(ingredient)
        );
        log.info("Ingredient updated successfully: {}", saved);
        return storageEntityMapper.mapToDomain(
                saved
        );
    }

    @Override
    public Optional<Ingredient> findIngredientByName(String ingredientName) {
        return storageJpaRepository.findIngredientByName(ingredientName);
    }

    @Override
    @Transactional
    public Map<String, Integer> getStorageMap() {
        return storageJpaRepository.findAll().stream()
                .collect(Collectors.toMap(
                        IngredientEntity::getName,
                        ingredient -> {
                            int factor = 1;
                            if (ingredient.getUnitName() != null && !ingredient.getUnitName().isEmpty()) {
                                try {
                                    factor = Unit.valueOf(ingredient.getUnitName()).getFactor();
                                } catch (IllegalArgumentException e) {
                                    log.warn("Unknown unit: {} for ingredient: {}, assuming GR", ingredient.getUnitName(), ingredient.getName());
                                }
                            }
                            int quantityInGrams = ingredient.getQuantity() * factor;
                            log.debug("Stock conversion: {} {} = {} GR", ingredient.getQuantity(), ingredient.getUnitName(), quantityInGrams);
                            return quantityInGrams;
                        },
                        Integer::sum
                ));
    }

    @Override
    public List<Ingredient> findAll() {
        List<IngredientEntity> ingredientEntityList = storageJpaRepository.findAll().stream().toList();
        return ingredientEntityList.stream().map(storageEntityMapper::mapToDomain).toList();
    }

    @Override
    public void deleteIngredientFromStorage(String ingredientId, String restaurantId) {
        storageJpaRepository.deleteById(ingredientId);
    }
}
