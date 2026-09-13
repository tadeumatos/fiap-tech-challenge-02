package br.com.fiap.tech.challenge._2.application.usecase.foodtype;

import br.com.fiap.tech.challenge._2.domain.FoodType;

public interface CreateFoodTypeUseCase {
    FoodType execute(String name);
}
