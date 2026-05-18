package com.example.shoppingcartservice.infrastructure.repository.jpa;

import com.example.shoppingcartservice.infrastructure.entity.ShoppingCartEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartJpaRepository  extends CrudRepository<ShoppingCartEntity, String> {

}
