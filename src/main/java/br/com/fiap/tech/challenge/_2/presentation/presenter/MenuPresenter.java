package br.com.fiap.tech.challenge._2.presentation.presenter;

import br.com.fiap.tech.challenge._2.domain.*;
import br.com.fiap.tech.challenge._2.presentation.controller.response.*;


import java.util.ArrayList;
import java.util.List;

public class MenuPresenter {
    private MenuPresenter() {
    }

    public static MenuResponse toResponse(Menu menu) {

        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.isOnlyLocal(),
                menu.getFoodPhoto(),
                new RestaurantResponse(menu.getRestaurant().getId(),
                                                           menu.getRestaurant().getName(),
                                                           menu.getRestaurant().getDescription(),
                                      new AddressResponse(
                                           menu.getRestaurant().getAddress().getId(),
                                           menu.getRestaurant().getAddress().getName(),
                                           menu.getRestaurant().getAddress().getNeighborhood(),
                                           menu.getRestaurant().getAddress().getCity(),
                                           menu.getRestaurant().getAddress().getState(),
                                           menu.getRestaurant().getAddress().getPostalCode(),
                                           menu.getRestaurant().getAddress().getCountry()
                                      ),
                                       menu.getRestaurant().getAddressNumber(),
                                       menu.getRestaurant().getAddressComplement(),
                                       new UserResponse(menu.getRestaurant().getUser().getId(),
                                                                      menu.getRestaurant().getUser().getName(),
                                                                      menu.getRestaurant().getUser().getEmail(),
                                               new UserTypeResponse(
                                                   menu.getRestaurant().getUser().getUserType().getId(),
                                                   menu.getRestaurant().getUser().getUserType().getName(),
                                                  menu.getRestaurant().getUser().getUserType().isOwner()
                                              )
                                      ),
                                      new FoodTypeResponse(menu.getRestaurant().getFoodType().getId(),
                                                           menu.getRestaurant().getFoodType().getName()),
                                      menu.getRestaurant().getStartTime(),
                                      menu.getRestaurant().getEndTime()

                        ),
                        menu.isActive()

        );
    }

    public static List<MenuResponse> toResponseList(List<Menu> list) {
        List<MenuResponse> responseList = new ArrayList<>();

        for (Menu menu :list) {

            responseList.add(
                    new MenuResponse(
                            menu.getId(),
                            menu.getName(),
                            menu.getDescription(),
                            menu.getPrice(),
                            menu.isOnlyLocal(),
                            menu.getFoodPhoto(),
                            new RestaurantResponse(menu.getRestaurant().getId(),
                                    menu.getRestaurant().getName(),
                                    menu.getRestaurant().getDescription(),
                                    new AddressResponse(
                                            menu.getRestaurant().getAddress().getId(),
                                            menu.getRestaurant().getAddress().getName(),
                                            menu.getRestaurant().getAddress().getNeighborhood(),
                                            menu.getRestaurant().getAddress().getCity(),
                                            menu.getRestaurant().getAddress().getState(),
                                            menu.getRestaurant().getAddress().getPostalCode(),
                                            menu.getRestaurant().getAddress().getCountry()
                                    ),
                                    menu.getRestaurant().getAddressNumber(),
                                    menu.getRestaurant().getAddressComplement(),
                                    new UserResponse(menu.getRestaurant().getUser().getId(),
                                            menu.getRestaurant().getUser().getName(),
                                            menu.getRestaurant().getUser().getEmail(),
                                            new UserTypeResponse(
                                                    menu.getRestaurant().getUser().getUserType().getId(),
                                                    menu.getRestaurant().getUser().getUserType().getName(),
                                                    menu.getRestaurant().getUser().getUserType().isOwner()
                                            )
                                    ),
                                    new FoodTypeResponse(menu.getRestaurant().getFoodType().getId(),
                                            menu.getRestaurant().getFoodType().getName()),
                                    menu.getRestaurant().getStartTime(),
                                    menu.getRestaurant().getEndTime()

                            ),
                            menu.isActive()

                    )
            );
        }
        return responseList;
    }

}
