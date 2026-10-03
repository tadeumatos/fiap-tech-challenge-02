package br.com.fiap.tech.challenge._2.application.usecase.restaurant;

import br.com.fiap.tech.challenge._2.domain.Restaurant;
import java.util.List;


public interface GetAllRestaurantUseCase {
    List<Restaurant> execute();
}
