package br.com.fiap.tech.challenge._2.application.usecase.restaurant;

import br.com.fiap.tech.challenge._2.domain.Restaurant;
import java.util.UUID;


public interface FindRestaurantUseCase {
    Restaurant execute(UUID id);
}
