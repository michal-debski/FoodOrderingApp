package com.example.shoppingcartservice.business.feign;

import com.example.shoppingcartservice.config.FeignClientInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "shopping-cart-service",
        url = "http://api-gateway-service:8222/",
        configuration = FeignClientInterceptor.class
)
public interface MealClient {
    @GetMapping("/api/v1/meals/meal/{id}")
    MealExternalResponse getMealById(@PathVariable("id") String id);
}
