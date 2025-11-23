package com.example.orderservice.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "meal-service",
        url = "http://localhost:8222/",
        configuration = FeignClientInterceptor.class
)
public interface MealClient {

    @PostMapping("/api/v1/meals/")
    ResponseEntity<List<MealDataResponse>> getAllMealsById(@RequestBody List<String> mealIds);
}