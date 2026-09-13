package br.com.fiap.tech.challenge._2.application.usecase.foodtype.Impl;

import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.foodtype.CreateFoodTypeUseCase;
import br.com.fiap.tech.challenge._2.domain.FoodType;

public class CreateFoodTypeUseCaseImpl implements CreateFoodTypeUseCase {
    private final FoodTypeGateway foodTypeGateway;

    public CreateFoodTypeUseCaseImpl(FoodTypeGateway foodTypeGateway) {
        this.foodTypeGateway = foodTypeGateway;
    }

    @Override
    public FoodType execute(String name) {
        FoodType foodType = FoodType.create(name);

        return foodTypeGateway.save(foodType);
    }
}
