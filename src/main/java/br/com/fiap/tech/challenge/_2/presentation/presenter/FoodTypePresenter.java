package br.com.fiap.tech.challenge._2.presentation.presenter;

import br.com.fiap.tech.challenge._2.domain.FoodType;
import br.com.fiap.tech.challenge._2.presentation.controller.response.FoodTypeResponse;

import java.util.ArrayList;
import java.util.List;

public class FoodTypePresenter {

    private FoodTypePresenter() {
    }

    public static FoodTypeResponse toResponse(FoodType foodType) {

        return new FoodTypeResponse(
                foodType.getId(),
                foodType.getName()
        );
    }

    public static List<FoodTypeResponse> toResponseList(List<FoodType> list) {
        List<FoodTypeResponse> responseList = new ArrayList<>();

        for (FoodType foodType:list) {

            responseList.add(
                    new FoodTypeResponse(
                            foodType.getId(),
                            foodType.getName()
                    )
            );
        }
        return responseList;
    }

}
