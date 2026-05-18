package com.example.shoppingcartservice.infrastructure.repository;

import com.example.shoppingcartservice.business.ShoppingCartDAO;
import com.example.shoppingcartservice.domain.ShoppingCart;
import com.example.shoppingcartservice.exception.ShoppingCartNotFoundException;
import com.example.shoppingcartservice.infrastructure.repository.jpa.ShoppingCartJpaRepository;
import com.example.shoppingcartservice.infrastructure.repository.mapper.ShoppingCartEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ShoppingCartRepository implements ShoppingCartDAO {

    private final ShoppingCartEntityMapper shoppingCartEntityMapper;
    private final ShoppingCartJpaRepository shoppingCartJpaRepository;

    @Override
    public Optional<ShoppingCart>  getShoppingCartForUser(String userId) {
        return shoppingCartJpaRepository.findById(userId)
                .map(shoppingCartEntityMapper::mapToDomain);
    }

    @Override
    public void deleteShoppingCart(String userId) {
        shoppingCartJpaRepository.deleteById(userId);
    }

    @Override
    public void updateShoppingCart(ShoppingCart shoppingCart) {
        shoppingCartJpaRepository.save(shoppingCartEntityMapper.mapToEntity(shoppingCart));
    }
}
