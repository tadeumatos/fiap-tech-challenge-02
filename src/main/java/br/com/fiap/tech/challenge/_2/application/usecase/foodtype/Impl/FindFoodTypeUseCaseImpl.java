package br.com.fiap.tech.challenge._2.application.usecase.foodtype.Impl;

import br.com.fiap.tech.challenge._2.application.exceptions.NotFoundException;
import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.foodtype.FindFoodTypeUseCase;
import br.com.fiap.tech.challenge._2.domain.FoodType;


import java.util.UUID;

public class FindFoodTypeUseCaseImpl implements FindFoodTypeUseCase {
    private final FoodTypeGateway foodTypeGateway;

    public FindFoodTypeUseCaseImpl(FoodTypeGateway foodTypeGateway) {
        this.foodTypeGateway = foodTypeGateway;
    }

    @Override
    public FoodType execute(UUID id) {

        return foodTypeGateway.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Food Type not found"));
    }
}
