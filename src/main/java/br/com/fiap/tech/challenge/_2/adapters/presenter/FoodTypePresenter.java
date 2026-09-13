package br.com.fiap.tech.challenge._2.adapters.presenter;

import br.com.fiap.tech.challenge._2.domain.FoodType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FoodTypePresenter {

    private FoodTypePresenter() {
    }

    public static FoodTypePresenter.FoodTypeResponse toResponse(FoodType foodType) {

        return new FoodTypePresenter.FoodTypeResponse(
                foodType.getId(),
                foodType.getName()
        );
    }

    public static List<FoodTypePresenter.FoodTypeResponse> toResponseList(List<FoodType> list) {
        List<FoodTypePresenter.FoodTypeResponse> responseList = new ArrayList<>();

        for (FoodType foodType:list) {

            responseList.add(
                    new FoodTypePresenter.FoodTypeResponse(
                            foodType.getId(),
                            foodType.getName()
                    )
            );
        }
        return responseList;
    }

    public record FoodTypeResponse(
            UUID id,
            String name
    ) {
    }
}
