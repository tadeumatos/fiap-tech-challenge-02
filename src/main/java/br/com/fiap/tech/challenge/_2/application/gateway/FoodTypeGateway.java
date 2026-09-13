package br.com.fiap.tech.challenge._2.application.gateway;

import br.com.fiap.tech.challenge._2.presentation.controller.request.CreateFoodTypeRequest;
import br.com.fiap.tech.challenge._2.domain.FoodType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodTypeGateway {
    FoodType save(FoodType foodType);
    Optional<FoodType> findById(UUID id);
    List<FoodType> getAll();
    void delete(UUID id);
    FoodType update(UUID id, CreateFoodTypeRequest createFoodTypeRequest);
}
