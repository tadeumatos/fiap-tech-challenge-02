package br.com.fiap.tech.challenge._2.application.usecase.restaurant.Impl;

import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.usecase.restaurant.GetAllRestaurantUseCase;
import br.com.fiap.tech.challenge._2.domain.Restaurant;

import java.util.List;

public class GetAllRestaurantUseCaseImpl implements GetAllRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;

    public GetAllRestaurantUseCaseImpl(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    public List<Restaurant> execute() {

        return restaurantGateway.getAll();
    }
}
