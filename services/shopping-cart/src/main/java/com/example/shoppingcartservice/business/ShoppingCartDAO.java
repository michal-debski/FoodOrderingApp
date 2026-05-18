package com.example.shoppingcartservice.business;

import com.example.shoppingcartservice.domain.ShoppingCart;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ShoppingCartDAO {

    Optional<ShoppingCart> getShoppingCartForUser(String userId);

    void deleteShoppingCart(String userId);

    void updateShoppingCart(ShoppingCart shoppingCart);

}
