package com.example.mealservice.business;

import com.example.mealservice.domain.MealCheckMessage;
import com.example.mealservice.domain.OrderItem;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaMealCheckConsumer {


    private MealMenuService mealMenuService;

    @KafkaListener(
            topics = "meal_check",
            groupId = "groupId",
            containerFactory = "factory"
    )
    void getMealChecks(MealCheckMessage mealCheckMessage) {
        System.out.println("Received: " + mealCheckMessage);
        mealMenuService.checkIfMealsCanBeOrdered(mealCheckMessage.getOrderItems()
                .stream()
                .map(OrderItem::getMealId)
                .toList()
        );
    }
}
