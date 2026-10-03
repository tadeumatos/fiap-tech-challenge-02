package br.com.fiap.tech.challenge._2.application.gateway;

import br.com.fiap.tech.challenge._2.domain.Restaurant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantGateway {
    Restaurant save(Restaurant restaurant);
    Optional<Restaurant> findById(UUID id);
    List<Restaurant> getAll();
    void delete(UUID id);
    Restaurant update(UUID id, Restaurant restaurant);
    boolean existFoodType(UUID id);
    boolean existUser(UUID id);
    boolean existAddress(UUID id);
}
