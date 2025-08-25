package com.example.orderservice.business;

import com.example.orderservice.api.dto.IngredientRemovalFromStorageMessage;
import com.example.orderservice.api.dto.mapper.OrderMapper;
import com.example.orderservice.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageProducerService {

    private final KafkaTemplate<String, IngredientRemovalFromStorageMessage> kafkaTemplate;
    private final OrderMapper orderMapper;

    public void sendMessage(Order order) {
        IngredientRemovalFromStorageMessage ingredientRemovalFromStorageMessage = orderMapper.mapToIngredientRemovalFromStorageMessage(order);
        kafkaTemplate.send("storage_remove", ingredientRemovalFromStorageMessage);
    }
}
