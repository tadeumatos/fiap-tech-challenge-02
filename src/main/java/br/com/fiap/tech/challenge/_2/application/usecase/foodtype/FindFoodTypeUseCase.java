package br.com.fiap.tech.challenge._2.application.usecase.foodtype;

import br.com.fiap.tech.challenge._2.domain.FoodType;

import java.util.UUID;

public interface FindFoodTypeUseCase {
    FoodType execute(UUID id);
}
