package br.com.fiap.tech.challenge._2.presentation.presenter;

import br.com.fiap.tech.challenge._2.domain.Restaurant;
import br.com.fiap.tech.challenge._2.presentation.controller.response.*;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RestaurantPresenter {
    private RestaurantPresenter() {
    }

    public static RestaurantResponse toResponse(Restaurant restaurant) {

        return new RestaurantResponse(
           restaurant.getId(),
           restaurant.getName(),
           restaurant.getDescription(),
           new AddressResponse(
                   restaurant.getAddress().getId(),
                   restaurant.getAddress().getName(),
                   restaurant.getAddress().getNeighborhood(),
                   restaurant.getAddress().getCity(),
                   restaurant.getAddress().getState(),
                   restaurant.getAddress().getPostalCode(),
                   restaurant.getAddress().getCountry()
           ),
           restaurant.getAddressNumber(),
           restaurant.getAddressComplement(),
           new UserResponse(
                   restaurant.getUser().getId(),
                   restaurant.getUser().getName(),
                   restaurant.getUser().getEmail(),
                   new UserTypeResponse(
                        restaurant.getUser().getUserType().getId(),
                        restaurant.getUser().getUserType().getName(),
                        restaurant.getUser().getUserType().isOwner())
           ),
           new FoodTypeResponse(
                  restaurant.getFoodType().getId(),
                  restaurant.getFoodType().getName()),
           restaurant.getStartTime(),
           restaurant.getEndTime()
        );
    }

    public static List<RestaurantResponse> toResponseList(List<Restaurant> list) {
        List<RestaurantResponse> responseList = new ArrayList<>();

        for (Restaurant restaurant :list) {

            responseList.add(
                    new RestaurantResponse(
                            restaurant.getId(),
                            restaurant.getName(),
                            restaurant.getDescription(),
                            new AddressResponse(
                                    restaurant.getAddress().getId(),
                                    restaurant.getAddress().getName(),
                                    restaurant.getAddress().getNeighborhood(),
                                    restaurant.getAddress().getCity(),
                                    restaurant.getAddress().getState(),
                                    restaurant.getAddress().getPostalCode(),
                                    restaurant.getAddress().getCountry()
                            ),
                            restaurant.getAddressNumber(),
                            restaurant.getAddressComplement(),
                            new UserResponse(
                                    restaurant.getUser().getId(),
                                    restaurant.getUser().getName(),
                                    restaurant.getUser().getEmail(),
                                    new UserTypeResponse(
                                            restaurant.getUser().getUserType().getId(),
                                            restaurant.getUser().getUserType().getName(),
                                            restaurant.getUser().getUserType().isOwner())
                            ),
                            new FoodTypeResponse(
                                    restaurant.getFoodType().getId(),
                                    restaurant.getFoodType().getName()),
                            restaurant.getStartTime(),
                            restaurant.getEndTime()
                    )
            );
        }
        return responseList;
    }
}
