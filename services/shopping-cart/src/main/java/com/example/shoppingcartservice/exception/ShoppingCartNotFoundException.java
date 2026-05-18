package com.example.shoppingcartservice.exception;

public class ShoppingCartNotFoundException extends RuntimeException {
    public ShoppingCartNotFoundException(String message) {
        super(message);
    }
}
