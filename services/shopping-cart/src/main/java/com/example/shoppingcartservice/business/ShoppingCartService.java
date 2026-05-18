package com.example.shoppingcartservice.business;

import com.example.shoppingcartservice.business.feign.MealClient;
import com.example.shoppingcartservice.business.feign.MealExternalResponse;
import com.example.shoppingcartservice.domain.CartItem;
import com.example.shoppingcartservice.domain.ShoppingCart;
import com.example.shoppingcartservice.exception.ShoppingCartNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ShoppingCartService {

    private final ShoppingCartDAO shoppingCartDAO;
    private final MealClient mealClient;

    public ShoppingCart getShoppingCartForUser(String userId) {
        log.info("Getting shopping cart for user {}", userId);
        ShoppingCart shoppingCart = shoppingCartDAO.getShoppingCartForUser(userId)
                .orElseThrow(() -> new ShoppingCartNotFoundException(
                        String.format("Shopping cart for user %s not found.", userId)));
        List<CartItem> cartItemsList = getCartItems(shoppingCart);
        BigDecimal totalPrice = cartItemsList.stream()
                .map(CartItem::getUnitPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ShoppingCart(
                shoppingCart.userEmail(),
                shoppingCart.restaurantId(),
                cartItemsList,
                totalPrice
        );
    }

    public ShoppingCart addProductToCart(String userId, String mealId) {
        MealExternalResponse mealById = mealClient.getMealById(mealId);
        log.info("!!! ODPOWIEDŹ Z MEAL SERVICE: {}", mealById);
        log.info("!!! RESTAURANT ID: {}", mealById.restaurantId());
        ShoppingCart shoppingCartForUser = shoppingCartDAO.getShoppingCartForUser(userId)
                .orElse(new ShoppingCart(userId, mealById.restaurantId(), new ArrayList<>(), BigDecimal.ZERO));
        List<CartItem> modifiableCartItems = new ArrayList<>(getCartItems(shoppingCartForUser));
        boolean anyMatch = modifiableCartItems.stream().anyMatch(t -> t.getMealId().equals(mealId));
        if (anyMatch) {
            log.info("Updating quantity for meal {} in shopping cart for user {}", mealId, userId);
            modifiableCartItems.stream()
                    .filter(t -> t.getMealId().equals(mealId))
                    .findFirst()
                    .ifPresent(t -> t.setQuantity(t.getQuantity() + 1));

        } else {
            log.info("Adding new meal {} to shopping cart for user {}", mealId, userId);
            modifiableCartItems.add(CartItem.builder()
                    .mealId(mealId)
                    .unitPrice(mealById.price())
                    .quantity(1)
                    .build());
        }
        ShoppingCart updatedShoppingCart = new ShoppingCart(
                shoppingCartForUser.userEmail(),
                mealById.restaurantId(),
                modifiableCartItems,
                shoppingCartForUser.totalPrice().add(mealById.price())
        );
        shoppingCartDAO.updateShoppingCart(updatedShoppingCart);

        log.info("Adding meal {} to shopping cart for user {}", mealId, userId);
        log.info("Actual total price: {}", updatedShoppingCart.totalPrice());
        return updatedShoppingCart;
    }

    public void clearCart(String userId) {
        log.info("Clearing shopping cart for user {}", userId);
        shoppingCartDAO.deleteShoppingCart(userId);
    }

    public void removeMealFromCart(String userId, String mealId) {
        MealExternalResponse mealById = mealClient.getMealById(mealId);
        ShoppingCart shoppingCartForUser = shoppingCartDAO.getShoppingCartForUser(userId)
                .orElse(new ShoppingCart(userId, mealById.restaurantId(), new ArrayList<>(), BigDecimal.ZERO));
        List<CartItem> modifiableCartItems = new ArrayList<>(getCartItems(shoppingCartForUser));
        log.info("Removing meal {} from shopping cart for user {}", mealId, userId);
        modifiableCartItems.removeIf(t -> t.getMealId().equals(mealId));

        if (modifiableCartItems.isEmpty()) {
            shoppingCartDAO.deleteShoppingCart(userId);
            log.info("Shopping cart for user {} is now empty and has been deleted", userId);
        }
            log.info("Updated shopping cart for user {} after removing meal {}", userId, mealId);
            ShoppingCart updatedShoppingCart = new ShoppingCart(
                    shoppingCartForUser.userEmail(),
                    shoppingCartForUser.restaurantId(),
                    modifiableCartItems,
                    shoppingCartForUser.totalPrice().subtract(mealById.price())
            );
            shoppingCartDAO.updateShoppingCart(updatedShoppingCart);
            log.info("Actual total price: {}", updatedShoppingCart.totalPrice());

    }

    public ShoppingCart updateMealQuantityInCart(String userId, String mealId, Integer quantity) {
        MealExternalResponse mealById = mealClient.getMealById(mealId);
        ShoppingCart shoppingCartForUser = shoppingCartDAO.getShoppingCartForUser(userId)
                .orElse(new ShoppingCart(userId, mealById.restaurantId(), new ArrayList<>(), BigDecimal.ZERO));
        List<CartItem> modifiableCartItems = getCartItems(shoppingCartForUser);
        log.info("Updating shopping cart for user {}", userId);
        modifiableCartItems.stream()
                .filter(cartItem -> cartItem.getMealId().equals(mealId))
                .findFirst()
                .ifPresent(cartItem ->
                    cartItem.setQuantity(quantity));
        BigDecimal newTotalPrice = modifiableCartItems.stream()
                .map(cartItem -> cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Calculated new total price for shopping cart of user {}: {}", userId, newTotalPrice);
        ShoppingCart updatedShoppingCart = new ShoppingCart(
                shoppingCartForUser.userEmail(),
                shoppingCartForUser.restaurantId(),
                modifiableCartItems,
                newTotalPrice
        );
        shoppingCartDAO.updateShoppingCart(updatedShoppingCart);
        log.info("Updated shopping cart for user {} after updating meal quantity", userId);
        return updatedShoppingCart;
    }

    private List<CartItem> getCartItems(ShoppingCart shoppingCart) {
        return shoppingCart.cartItemDTOList()
                .stream()
                .map(cartItem -> {
                    MealExternalResponse mealById = mealClient.getMealById(cartItem.getMealId());
                    return CartItem.builder()
                            .mealId(cartItem.getMealId())
                            .mealName(mealById.name())
                            .unitPrice(mealById.price())
                            .quantity(cartItem.getQuantity())
                            .build();
                })
                .toList();
    }
}

