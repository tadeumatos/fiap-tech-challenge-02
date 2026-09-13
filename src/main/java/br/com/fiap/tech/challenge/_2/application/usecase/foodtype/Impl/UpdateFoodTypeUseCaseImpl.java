package br.com.fiap.tech.challenge._2.application.usecase.foodtype.Impl;

import br.com.fiap.tech.challenge._2.adapters.controller.request.CreateFoodTypeRequest;
import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.foodtype.UpdateFoodTypeUseCase;
import br.com.fiap.tech.challenge._2.domain.FoodType;

import java.util.UUID;

public class UpdateFoodTypeUseCaseImpl implements UpdateFoodTypeUseCase {
    private final FoodTypeGateway foodTypeGateway;

    public UpdateFoodTypeUseCaseImpl(FoodTypeGateway foodTypeGateway) {

        this.foodTypeGateway = foodTypeGateway;
    }

    @Override
    public FoodType execute(UUID id, CreateFoodTypeRequest createFoodTypeRequest) {
        return foodTypeGateway.update(id,createFoodTypeRequest);
    }
}
