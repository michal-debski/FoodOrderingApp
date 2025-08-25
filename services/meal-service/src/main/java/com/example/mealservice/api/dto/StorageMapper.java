package com.example.mealservice.api.dto;

import com.example.mealservice.domain.Ingredient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StorageMapper {

    public Ingredient mapToDomain(IngredientDTO ingredientDTO) {
        return Ingredient.builder()
                .name(ingredientDTO.name())
                .quantity(ingredientDTO.quantity())
                .unitName(ingredientDTO.unitName())
                .build();
    }

    public Ingredient mapIngredientRequestToDomain(IngredientRequest ingredientRequest) {
        return Ingredient.builder()
                .name(ingredientRequest.name())
                .unitName(ingredientRequest.unitName())
                .build();
    }

    public IngredientDTO mapToDto(Ingredient ingredient) {
        return IngredientDTO.builder()
                .name(ingredient.name())
                .quantity(ingredient.quantity())
                .unitName(ingredient.unitName())
                .build();
    }

    public StorageConditionResponse mapToStorageCondition(List<Ingredient> ingredientList) {
        return StorageConditionResponse.builder()
                .ingredients(ingredientList.stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
