package com.example.restaurantservice.api.controller;

import com.example.restaurantservice.api.request.RestaurantRequest;
import com.example.restaurantservice.api.response.RestaurantDTO;
import com.example.restaurantservice.api.response.mapper.RestaurantMapper;
import com.example.restaurantservice.business.RestaurantService;
import com.example.restaurantservice.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;


    @PostMapping("/addRestaurant")
    public ResponseEntity<?> makeRestaurant(
            @RequestBody RestaurantRequest request
    ) {
        Restaurant restaurant = restaurantMapper.mapFromDto(request);
        Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurant);
        RestaurantDTO savedRestaurantInDTO = restaurantMapper.map(savedRestaurant);
        return new ResponseEntity<>(savedRestaurantInDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> deleteRestaurant(
            @PathVariable String restaurantId
    ) {
        restaurantService.deleteRestaurant(Integer.valueOf(restaurantId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


