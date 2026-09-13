package br.com.fiap.tech.challenge._2.application.usecase.foodtype;

import br.com.fiap.tech.challenge._2.domain.FoodType;
import java.util.List;

public interface GetAllFoodTypeUseCase {
    List<FoodType> execute();
}
