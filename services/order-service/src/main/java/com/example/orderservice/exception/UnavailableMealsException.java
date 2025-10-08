package com.example.orderservice.exception;

import com.example.mealservice.grpc.UnavailableMeal;
import lombok.Getter;

import java.util.List;

@Getter
public class UnavailableMealsException extends RuntimeException {
    private final List<UnavailableMeal> unavailableMeals;
    public UnavailableMealsException(List<UnavailableMeal> unavailableMeals) {
        super("No ingredients for meals: "+ unavailableMeals);
        this.unavailableMeals = unavailableMeals;
    }
}
