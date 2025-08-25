package com.example.orderservice.exception;

public class UnavailableMealsException extends RuntimeException {
    public UnavailableMealsException(String message) {
        super(message);
    }
}
