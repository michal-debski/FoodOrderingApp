package com.example.shoppingcartservice.api.controller;

import com.example.shoppingcartservice.api.response.MealItemRequest;
import com.example.shoppingcartservice.api.response.MealItemRequestToUpdateQuantity;
import com.example.shoppingcartservice.domain.ShoppingCart;
import com.example.shoppingcartservice.business.ShoppingCartService;
import com.example.shoppingcartservice.api.response.ShoppingCartDTO;
import com.example.shoppingcartservice.api.response.ShoppingCartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartMapper shoppingCartMapper;

    @GetMapping
    public ResponseEntity<ShoppingCartDTO> getShoppingCart(
            @RequestHeader("X-User-Email") String email
    ) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartForUser(email);
        ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.mapToDTO(shoppingCart);
        return ResponseEntity.ok(shoppingCartDTO);
    }

    @PostMapping
    public ResponseEntity<ShoppingCartDTO> addToCart(
            @RequestHeader("X-User-Email") String email,
            @RequestBody MealItemRequest mealItemRequest
    ) {
        ShoppingCart updatedShoppingCart = shoppingCartService.addProductToCart(email, mealItemRequest.mealId());
        ShoppingCartDTO shoppingCart = shoppingCartMapper.mapToDTO(updatedShoppingCart);
        return ResponseEntity.ok(shoppingCart);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestHeader("X-User-Email") String email) {
        shoppingCartService.clearCart(email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/meals/{mealId}")
    public ResponseEntity<Void> removeMealFromCart(
            @RequestHeader("X-User-Email") String email,
            @PathVariable String mealId
    ) {
        shoppingCartService.removeMealFromCart(email, mealId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/meals")
    public ResponseEntity<ShoppingCartDTO> updateMealQuantityInCart(
            @RequestHeader("X-User-Email") String email,
            @RequestBody MealItemRequestToUpdateQuantity mealItemRequest
    ) {
        ShoppingCart shoppingCart = shoppingCartService.updateMealQuantityInCart(email, mealItemRequest.mealId(), mealItemRequest.quantity());
        ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.mapToDTO(shoppingCart);
        return ResponseEntity.ok(shoppingCartDTO);
    }
}
