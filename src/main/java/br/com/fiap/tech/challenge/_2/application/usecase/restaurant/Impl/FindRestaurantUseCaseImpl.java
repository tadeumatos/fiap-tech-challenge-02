package br.com.fiap.tech.challenge._2.application.usecase.restaurant.Impl;

import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.usecase.restaurant.FindRestaurantUseCase;
import br.com.fiap.tech.challenge._2.domain.Restaurant;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;

import java.util.UUID;

public class FindRestaurantUseCaseImpl implements FindRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;

    public FindRestaurantUseCaseImpl(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;

    }

    @Override
    public Restaurant execute(UUID id) {

        return restaurantGateway.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("The restaurant not found"));
    }
}
