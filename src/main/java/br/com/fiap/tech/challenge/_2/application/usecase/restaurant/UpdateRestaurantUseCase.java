package br.com.fiap.tech.challenge._2.application.usecase.restaurant;

import br.com.fiap.tech.challenge._2.domain.Restaurant;
import br.com.fiap.tech.challenge._2.presentation.controller.request.RestaurantRequest;
import java.util.UUID;


public interface UpdateRestaurantUseCase {
    Restaurant execute(UUID id, RestaurantRequest request);
}
