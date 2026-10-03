package br.com.fiap.tech.challenge._2.infrastructure.config;


import br.com.fiap.tech.challenge._2.application.gateway.MenuGateway;
import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;

import br.com.fiap.tech.challenge._2.application.usecase.menu.*;
import br.com.fiap.tech.challenge._2.application.usecase.menu.impl.*;

import br.com.fiap.tech.challenge._2.application.usecase.restaurant.Impl.FindRestaurantUseCaseImpl;
import br.com.fiap.tech.challenge._2.infrastructure.gateway.MenuGatewayImpl;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.MenuRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.RestaurantRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuUseCaseConfig {
    @Bean
    public CreateMenuUseCase createMenuUseCase(
            MenuGateway menuGateway,
            RestaurantGateway restaurantGateway


    ) {
        return new CreateMenuUseCaseImpl(menuGateway,restaurantGateway);
    }

    @Bean
    public FindMenuUseCase findMenuUseCase(
            MenuGateway menuGateway
    ) {
        return new FindMenuUseCaseImpl(menuGateway);
    }

    @Bean
    public GetAllMenuUseCase getAllMenuUseCase(
            MenuGateway menuGateway
    ) {
        return new GetAllMenuUseCaseImpl(menuGateway);
    }

    @Bean
    public DeleteMenuUseCase deleteMenuUseCase(
            MenuGateway menuGateway
    ) {
        return new DeleteMenuUseCaseImpl(menuGateway);
    }

    @Bean
    public UpdateMenuUseCase updateMenuUseCase(
            MenuGateway menuGateway,
            RestaurantGateway restaurantGateway
    ) {
        return new UpdateMenuUseCaseImpl(menuGateway,restaurantGateway);
    }

    @Bean
    public MenuGatewayImpl MenuGatewayImpl(
            MenuRepository repository,
            RestaurantRepository restaurantRepository

    ) {
        return new MenuGatewayImpl(repository,restaurantRepository);
    }

}
