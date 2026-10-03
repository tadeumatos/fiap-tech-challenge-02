package br.com.fiap.tech.challenge._2.application.usecase.address.impl;

import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.usecase.address.DeleteAddressUseCase;
import br.com.fiap.tech.challenge._2.exceptions.EntityInUseException;


import java.util.UUID;

public class DeleteAddressUseCaseImpl implements DeleteAddressUseCase {

    private final AddressGateway addressGateway;
    private final RestaurantGateway restaurantGatewayGateway;

    public DeleteAddressUseCaseImpl(AddressGateway addressGateway,RestaurantGateway restaurantGatewayGateway) {
        this.addressGateway = addressGateway;
        this.restaurantGatewayGateway = restaurantGatewayGateway;
    }

    @Override
    public void execute(UUID id) {

        if(deleteAddressValidation(id)){
            throw new EntityInUseException("The cannot be deleted, the register this in use");
        }

        addressGateway.delete(id);
    }

    private boolean deleteAddressValidation(UUID id)
    {
        return restaurantGatewayGateway.existAddress(id);
    }
}
