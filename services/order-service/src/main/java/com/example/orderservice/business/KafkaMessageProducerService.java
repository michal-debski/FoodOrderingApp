package com.example.orderservice.business;

import com.example.orderservice.api.dto.IngredientRemovalFromStorageMessage;
import com.example.orderservice.api.dto.mapper.OrderMapper;
import com.example.orderservice.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageProducerService {

    private final KafkaTemplate<String, IngredientRemovalFromStorageMessage> kafkaTemplate;
    private final OrderMapper orderMapper;

    public void sendMessage(Order order) {
        IngredientRemovalFromStorageMessage ingredientRemovalFromStorageMessage = orderMapper.mapToIngredientRemovalFromStorageMessage(order);

                kafkaTemplate.send("storage_remove", ingredientRemovalFromStorageMessage)
                        .whenComplete((result, ex) -> {
                            if (ex != null) {
                                log.error("❌ Kafka send failed for message: {}", ingredientRemovalFromStorageMessage, ex);
                            } else {
                                log.info("✅ Kafka message sent successfully to topic: {}, partition: {}, offset: {}",
                                        result.getRecordMetadata().topic(),
                                        result.getRecordMetadata().partition(),
                                        result.getRecordMetadata().offset());
                            }
                        });
    }
}
