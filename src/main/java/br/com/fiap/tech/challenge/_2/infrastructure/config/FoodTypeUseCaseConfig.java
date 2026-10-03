package br.com.fiap.tech.challenge._2.infrastructure.config;

import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.usecase.foodtype.*;
import br.com.fiap.tech.challenge._2.application.usecase.foodtype.impl.*;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.FoodTypeRepository;

import br.com.fiap.tech.challenge._2.infrastructure.gateway.FoodTypeGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class FoodTypeUseCaseConfig {
    @Bean
    public CreateFoodTypeUseCase createFoodTypeUseCase(
            FoodTypeGateway foodTypeGateway
    ) {
        return new CreateFoodTypeUseCaseImpl(foodTypeGateway);
    }

    @Bean
    public FindFoodTypeUseCase findFoodTypeUseCase(
            FoodTypeGateway foodTypeGateway
    ) {
        return new FindFoodTypeUseCaseImpl(foodTypeGateway);
    }

    @Bean
    public GetAllFoodTypeUseCase getAllFoodTypeUseCase(
            FoodTypeGateway foodTypeGateway
    ) {
        return new GetAllFoodTypeUseCaseImpl(foodTypeGateway);
    }

    @Bean
    public DeleteFoodTypeUseCase deleteFoodTypeUseCase(
            FoodTypeGateway foodTypeGateway,
            RestaurantGateway restaurantGateway
    ) {
        return new DeleteFoodTypeUseCaseImpl(foodTypeGateway,restaurantGateway);
    }

    @Bean
    public UpdateFoodTypeUseCase updateFoodTypeUseCase(
            FoodTypeGateway foodTypeGateway
    ) {
        return new UpdateFoodTypeUseCaseImpl(foodTypeGateway);
    }

    @Bean
    public FoodTypeGatewayImpl foodTypeGatewayImpl(
            FoodTypeRepository foodTypeRepository
    ) {
        return new FoodTypeGatewayImpl(foodTypeRepository);
    }

}
