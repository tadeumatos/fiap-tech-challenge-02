package br.com.fiap.tech.challenge._2.application.usecase.restaurant.Impl;

import br.com.fiap.tech.challenge._2.application.gateway.MenuGateway;
import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.usecase.restaurant.DeleteRestaurantUseCase;
import br.com.fiap.tech.challenge._2.exceptions.EntityInUseException;

import java.util.UUID;

public class DeleteRestaurantUseCaseImpl implements DeleteRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;
    private final MenuGateway menuGateway;

    public DeleteRestaurantUseCaseImpl(RestaurantGateway restaurantGateway,MenuGateway menuGateway) {

        this.restaurantGateway = restaurantGateway;
        this.menuGateway=menuGateway;
    }

    @Override
    public void execute(UUID id) {

        if(deleteRestaurantValidation(id)){
            throw new EntityInUseException("The cannot be deleted, the register this in use");
        }

        restaurantGateway.delete(id);
    }

    private boolean deleteRestaurantValidation(UUID id)
    {
        return menuGateway.existRestaurant(id);
    }
}
