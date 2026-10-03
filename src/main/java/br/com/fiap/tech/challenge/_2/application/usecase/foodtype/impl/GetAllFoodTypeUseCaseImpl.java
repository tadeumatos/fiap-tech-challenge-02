package br.com.fiap.tech.challenge._2.application.usecase.foodtype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.foodtype.GetAllFoodTypeUseCase;
import br.com.fiap.tech.challenge._2.domain.FoodType;

import java.util.List;

public class GetAllFoodTypeUseCaseImpl implements GetAllFoodTypeUseCase {
    private final FoodTypeGateway foodTypeGateway;

    public GetAllFoodTypeUseCaseImpl(FoodTypeGateway foodTypeGateway)
    {
        this.foodTypeGateway = foodTypeGateway;
    }

    @Override
    public List<FoodType> execute() {
        return foodTypeGateway.getAll();
    }
}
