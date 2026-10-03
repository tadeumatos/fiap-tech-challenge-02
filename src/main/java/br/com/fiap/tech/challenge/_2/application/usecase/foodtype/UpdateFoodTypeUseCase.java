package br.com.fiap.tech.challenge._2.application.usecase.foodtype;

import br.com.fiap.tech.challenge._2.domain.FoodType;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.FoodTypeEntity;
import br.com.fiap.tech.challenge._2.presentation.controller.request.FoodTypeRequest;

import java.util.UUID;

public interface UpdateFoodTypeUseCase {
    FoodType execute(UUID id, FoodTypeRequest request);
}

