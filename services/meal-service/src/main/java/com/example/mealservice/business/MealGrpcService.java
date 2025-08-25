package com.example.mealservice.business;

import com.example.mealservice.domain.Meal;
import com.example.mealservice.grpc.MealCheckResponse;
import com.example.mealservice.grpc.MealServiceGrpc;
import com.example.mealservice.grpc.UnavailableMeal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@GrpcService
public class MealGrpcService extends MealServiceGrpc.MealServiceImplBase {

    private final MealMenuService mealMenuService;

    private final StorageService storageService;
    @Override
    public void checkMealAvailability(
            com.example.mealservice.grpc.MealCheckRequest request,
            io.grpc.stub.StreamObserver<com.example.mealservice.grpc.MealCheckResponse> responseObserver
    ) {
        try {

            log.info("Starting checkMealAvailability method in grpc");
            List<Meal> meals = mealMenuService.findMealsWhichCannotBePrepared(request.getOrderItemsList()
                    .stream()
                    .map(orderItem -> orderItem.getMealId())
                    .collect(Collectors.toList())
            );
            log.info("Found {} meals,  MEALS: {}", meals.size(), meals);
            if (meals.isEmpty()){
                MealCheckResponse mealCheckResponse = MealCheckResponse.newBuilder()
                        .setCanPrepare(true)
                        .build();
                responseObserver.onNext(mealCheckResponse);
                responseObserver.onCompleted();
            } else {
                List<UnavailableMeal> unavailableMeals = meals.stream()
                        .map(meal -> UnavailableMeal.newBuilder()
                                .setMealId(meal.mealId())
                                .setReason("No ingredients")
                                .build()
                        )
                        .toList();
                MealCheckResponse mealCheckResponse = MealCheckResponse.newBuilder()
                        .setCanPrepare(false)
                        .addAllUnavailableMeals(unavailableMeals)
                        .build();

                responseObserver.onNext(mealCheckResponse);
                responseObserver.onCompleted();
            }
        }catch (Exception e) {
            MealCheckResponse mealCheckResponse = MealCheckResponse.newBuilder()
                    .setCanPrepare(false)
                    .build();
            responseObserver.onNext(mealCheckResponse);
            responseObserver.onCompleted();
        }

    }
}
