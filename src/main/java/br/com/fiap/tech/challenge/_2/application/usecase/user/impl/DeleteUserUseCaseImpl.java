package br.com.fiap.tech.challenge._2.application.usecase.user.impl;

import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.application.usecase.user.DeleteUserUseCase;
import br.com.fiap.tech.challenge._2.exceptions.EntityInUseException;

import java.util.UUID;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserGateway userGateway;
    private final RestaurantGateway restaurantGateway;

    public DeleteUserUseCaseImpl(UserGateway userGateway,RestaurantGateway restaurantGateway) {
        this.userGateway = userGateway;
        this.restaurantGateway=restaurantGateway;
    }

    @Override
    public void execute(UUID id) {

        if(deleteUserValidation(id)){
            throw new EntityInUseException("The cannot be deleted, the register this in use");
        }

        userGateway.delete(id);
    }

    private boolean deleteUserValidation(UUID id)
    {
        return restaurantGateway.existUser(id);
    }
}
