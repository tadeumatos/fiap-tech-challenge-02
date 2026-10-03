package br.com.fiap.tech.challenge._2.application.usecase.foodtype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.usecase.foodtype.DeleteFoodTypeUseCase;
import br.com.fiap.tech.challenge._2.exceptions.EntityInUseException;

import java.util.UUID;

public class DeleteFoodTypeUseCaseImpl implements DeleteFoodTypeUseCase {
    private final FoodTypeGateway foodTypeGateway;
    private final RestaurantGateway restaurantGateway;

    public DeleteFoodTypeUseCaseImpl(FoodTypeGateway foodTypeGateway,RestaurantGateway restaurantGateway) {
        this.foodTypeGateway = foodTypeGateway;
        this.restaurantGateway =restaurantGateway;
    }

    @Override
    public void execute(UUID id) {
        if(deleteFoodTypeValidation(id)){
            throw new EntityInUseException("The cannot be deleted, the register this in use");
        }

        foodTypeGateway.delete(id);
    }

    private boolean deleteFoodTypeValidation(UUID id)
    {
        return restaurantGateway.existFoodType(id);
    }
}
