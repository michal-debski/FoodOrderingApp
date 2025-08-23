package com.example.orderservice.business;

import com.example.orderservice.api.dto.MealCheckMessage;
import com.example.orderservice.api.dto.OrderDTO;
import com.example.orderservice.api.dto.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageProducerService {

    private final KafkaTemplate<String, MealCheckMessage> kafkaTemplate;
    private final OrderMapper orderMapper;

    public void sendMessage(OrderDTO order) {
        MealCheckMessage mealCheckMessage = orderMapper.mapToMealCheckMessage(order);
        kafkaTemplate.send("meal_check", mealCheckMessage);

    }

}
