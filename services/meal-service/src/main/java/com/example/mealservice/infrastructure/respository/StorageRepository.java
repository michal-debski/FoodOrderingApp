package com.example.mealservice.infrastructure.respository;

import com.example.mealservice.business.StorageDAO;
import com.example.mealservice.domain.Ingredient;
import com.example.mealservice.infrastructure.entity.IngredientEntity;
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
    public Ingredient addNewIngredientToStore(Ingredient ingredient, String restaurantId) {
        IngredientEntity ingredientEntity = storageEntityMapper.mapToEntity(ingredient);
        ingredientEntity.setRestaurantId(restaurantId);
        IngredientEntity savedIngredient = storageJpaRepository.save(ingredientEntity);
        log.info("Ingredient added to database");
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
                        IngredientEntity::getQuantity,
                        Integer::sum
                ));
    }

    @Override
    public List<Ingredient> findAll() {
        List<IngredientEntity> ingredientEntityList = storageJpaRepository.findAll().stream().toList();
        return ingredientEntityList.stream().map(storageEntityMapper::mapToDomain).toList();
    }
}
