package com.example.orderservice.business;

import com.example.mealservice.grpc.MealCheckRequest;
import com.example.mealservice.grpc.MealCheckResponse;
import com.example.mealservice.grpc.MealServiceGrpc;
import com.example.orderservice.domain.OrderItem;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MealServiceGrpcClient {


    private final MealServiceGrpc.MealServiceBlockingStub stub;

    public MealServiceGrpcClient(
            @Value("${order.service.address:localhost}") String serverAddress,
            @Value("${order.service.grpc:9001}") int serverPort
    ) {

        log.info("Connecting to Meal Service GRPC server at {} {}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(serverAddress, serverPort)
                .usePlaintext()
                .build();

        stub = MealServiceGrpc.newBlockingStub(channel);
    }

    public MealCheckResponse checkMealAvailability(String orderNumber, List<OrderItem> orderItems) {
        log.info("Starting checking meals availability. Waiting for response.");
        List<com.example.mealservice.grpc.OrderItem> protoItems = orderItems.stream()
                .map(item -> com.example.mealservice.grpc.OrderItem.newBuilder()
                        .setMealId(item.getMealId())
                        .setQuantity(item.getQuantity())
                        .build())
                .toList();
        MealCheckRequest request = MealCheckRequest.newBuilder()
                .setOrderNumber(orderNumber)
                .addAllOrderItems(protoItems)
                .build();
        MealCheckResponse response = stub.checkMealAvailability(request);

        log.info("Received response with meals availability: {}", response);
        return response;

    }
}
