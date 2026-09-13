package br.com.fiap.tech.challenge._2.application.usecase.foodtype.Impl;

import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.foodtype.DeleteFoodTypeUseCase;

import java.util.UUID;

public class DeleteFoodTypeUseCaseImpl implements DeleteFoodTypeUseCase {
    private final FoodTypeGateway foodTypeGateway;

    public DeleteFoodTypeUseCaseImpl(FoodTypeGateway foodTypeGateway) {
        this.foodTypeGateway = foodTypeGateway;
    }

    @Override
    public void execute(UUID id) {
        foodTypeGateway.delete(id);
    }
}
