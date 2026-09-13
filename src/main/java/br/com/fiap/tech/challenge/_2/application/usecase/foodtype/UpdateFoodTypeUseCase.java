package br.com.fiap.tech.challenge._2.application.usecase.foodtype;

import br.com.fiap.tech.challenge._2.presentation.controller.request.CreateFoodTypeRequest;
import br.com.fiap.tech.challenge._2.domain.FoodType;


import java.util.UUID;

public interface UpdateFoodTypeUseCase {
    FoodType execute(UUID id, CreateFoodTypeRequest createFoodTypeRequest);
}

