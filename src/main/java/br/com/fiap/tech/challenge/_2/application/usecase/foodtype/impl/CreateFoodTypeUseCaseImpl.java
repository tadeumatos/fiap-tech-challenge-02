package br.com.fiap.tech.challenge._2.application.usecase.foodtype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.foodtype.CreateFoodTypeUseCase;
import br.com.fiap.tech.challenge._2.domain.FoodType;
import br.com.fiap.tech.challenge._2.presentation.controller.request.FoodTypeRequest;

import java.util.UUID;

public class CreateFoodTypeUseCaseImpl implements CreateFoodTypeUseCase {
    private final FoodTypeGateway foodTypeGateway;

    public CreateFoodTypeUseCaseImpl(FoodTypeGateway foodTypeGateway) {
        this.foodTypeGateway = foodTypeGateway;
    }

    @Override
    public FoodType execute(FoodTypeRequest request) {
        FoodType foodType = FoodType.create(UUID.randomUUID(),request.name());
        return foodTypeGateway.save(foodType);
    }
}
