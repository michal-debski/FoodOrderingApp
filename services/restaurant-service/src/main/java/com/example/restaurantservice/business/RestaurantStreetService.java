package com.example.restaurantservice.business;

import com.example.restaurantservice.business.dao.RestaurantStreetDAO;
import com.example.restaurantservice.domain.Restaurant;
import com.example.restaurantservice.domain.RestaurantStreet;
import com.example.restaurantservice.domain.Street;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantStreetService {

    private final RestaurantStreetDAO restaurantStreetDAO;

    private final StreetService streetService;

    public List<RestaurantStreet> findAllRestaurantStreets() {
        List<RestaurantStreet> restaurantStreets = restaurantStreetDAO.findAllRestaurantStreets();
        log.info("Available streets: [{}]", restaurantStreets.size());
        return restaurantStreets;
    }

    @Transactional
    public void saveRestaurantStreets(Restaurant restaurant, List<String> streets) {

        List<Integer> streetsId = streets.stream()
                .map(Integer::valueOf)
                .toList();
        List<RestaurantStreet> restaurantStreetsList = new ArrayList<>();
        preparationOfRestaurantStreets(restaurant, streetsId, restaurantStreetsList);
        log.info("Trying to save all given/prepared restaurant streets");
        saveAllGivenStreets(restaurantStreetsList);
    }

    private void saveAllGivenStreets(List<RestaurantStreet> restaurantStreetsList) {
        for (RestaurantStreet restaurantStreet : restaurantStreetsList) {

            restaurantStreetDAO.saveRestaurantStreet(restaurantStreet);

        }
    }

    private void preparationOfRestaurantStreets(Restaurant restaurant, List<Integer> streetsId, List<RestaurantStreet> restaurantStreetsList) {
        for (Integer streetId : streetsId) {
            Street street = streetService.findByStreetId(streetId);
            RestaurantStreet restaurantStreet = RestaurantStreet
                    .builder()
                    .street(street)
                    .restaurant(restaurant)
                    .build();
            restaurantStreetsList.add(restaurantStreet);

        }
    }

}
