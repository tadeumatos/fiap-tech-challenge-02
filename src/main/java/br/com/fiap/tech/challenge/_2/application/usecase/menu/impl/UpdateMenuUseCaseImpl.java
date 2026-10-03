package br.com.fiap.tech.challenge._2.application.usecase.menu.impl;

import br.com.fiap.tech.challenge._2.application.gateway.MenuGateway;
import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.usecase.menu.UpdateMenuUseCase;
import br.com.fiap.tech.challenge._2.domain.Menu;
import br.com.fiap.tech.challenge._2.domain.Restaurant;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.presentation.controller.request.MenuRequest;

import java.util.UUID;

public class UpdateMenuUseCaseImpl implements UpdateMenuUseCase {
    private final MenuGateway menuGateway;
    private final RestaurantGateway restaurantGateway;


    public UpdateMenuUseCaseImpl(MenuGateway menuGateway,RestaurantGateway restaurantGateway) {
        this.menuGateway=menuGateway;
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    public Menu execute(UUID id, MenuRequest request) {
        Restaurant restaurant = restaurantGateway.findById(UUID.fromString(request.restaurantId())).orElseThrow(()->  new ResourceNotFoundException("The restaurant not found"));

        Menu menu = Menu.create(UUID.randomUUID(),request.name(), request.description(),request.price(),request.onlyLocal(),request.foodPhoto(),restaurant,request.active());
        return menuGateway.update(id, menu);
    }

}
