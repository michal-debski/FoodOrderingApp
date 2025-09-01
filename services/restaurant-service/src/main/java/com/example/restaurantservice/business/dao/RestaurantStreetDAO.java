package com.example.restaurantservice.business.dao;


import com.example.restaurantservice.domain.Restaurant;
import com.example.restaurantservice.domain.RestaurantStreet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RestaurantStreetDAO {

    List<RestaurantStreet> findAllRestaurantStreets();

    RestaurantStreet saveRestaurantStreet(RestaurantStreet restaurantStreet, Restaurant restaurant);

}
