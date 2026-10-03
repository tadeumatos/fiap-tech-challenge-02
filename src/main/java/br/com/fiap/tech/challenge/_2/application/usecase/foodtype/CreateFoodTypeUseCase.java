package br.com.fiap.tech.challenge._2.application.usecase.foodtype;

import br.com.fiap.tech.challenge._2.domain.FoodType;
import br.com.fiap.tech.challenge._2.presentation.controller.request.FoodTypeRequest;

public interface CreateFoodTypeUseCase {
    FoodType execute(FoodTypeRequest request);
}
