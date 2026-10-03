package br.com.fiap.tech.challenge._2.application.usecase.restaurant;

import br.com.fiap.tech.challenge._2.domain.Restaurant;
import br.com.fiap.tech.challenge._2.presentation.controller.request.RestaurantRequest;

public interface CreateRestaurantUseCase {
    Restaurant execute(RestaurantRequest request);
}
