package com.example.mealservice.api;

import com.example.mealservice.api.dto.MealDTO;
import com.example.mealservice.api.dto.MealMapper;
import com.example.mealservice.api.dto.MealUpdateRequest;
import com.example.mealservice.business.MealMenuService;
import com.example.mealservice.domain.Meal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{restaurantId}/meals")
public class MealController {
    private final MealMenuService mealMenuService;
    private final MealMapper mealMapper;

    @GetMapping
    public ResponseEntity<List<MealDTO>> showMeals(
            @PathVariable("restaurantId") String restaurantId
    ) {

        var meals = mealMenuService.findAllBySelectedRestaurant(restaurantId).stream()
                .map(mealMapper::mapToDTO)
                .toList();

        return new ResponseEntity<>(meals, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> addMealToMenu(
            @PathVariable String restaurantId,
            @RequestBody MealDTO mealDTO
    ) {

        Meal mealToSave = mealMapper.mapForSave(mealDTO, restaurantId);
        Meal meal = mealMenuService.makeMealForRestaurant(mealToSave);

        return new ResponseEntity<>(mealMapper.mapForSaveToDTO(meal), HttpStatus.OK);
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<?> deleteMealByRestaurant(
            @PathVariable String restaurantId,
            @PathVariable String mealId
    ) {
        mealMenuService.deleteMeal(mealId);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{mealId}")
    public ResponseEntity<MealDTO> updateMeal(
            @PathVariable Integer restaurantId,
            @PathVariable String mealId,
            @RequestBody MealUpdateRequest mealUpdateRequest
    ) {

        Meal updatedMeal = mealMenuService.updateMeal(mealUpdateRequest, mealId);
        MealDTO mealDTO = mealMapper.mapToDTO(updatedMeal);

        return new ResponseEntity<>(mealDTO, HttpStatus.OK);
    }
}
