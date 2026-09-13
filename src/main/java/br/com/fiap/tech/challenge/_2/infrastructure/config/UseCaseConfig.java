package br.com.fiap.tech.challenge._2.infrastructure.config;

import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.foodtype.*;
import br.com.fiap.tech.challenge._2.application.usecase.foodtype.Impl.*;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.*;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public CreateUserTypeUseCase createUserTypeUseCase(
            UserTypeGateway userTypeGateway
    ) {
        return new CreateUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public FindUserTypeUseCase findUserTypeUseCase(
            UserTypeGateway userTypeGateway
    ) {
        return new FindUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public GetAllUserTypeUseCase getAllUserTypeUseCase(
            UserTypeGateway userTypeGateway
    ) {
        return new GetAllUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public DeleteUserTypeUseCase deleteUserTypeUseCase(
            UserTypeGateway userTypeGateway
    ) {
        return new DeleteUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public UpdateUserTypeUseCase updateUserTypeUseCase(
            UserTypeGateway userTypeGateway
    ) {
        return new UpdateUserTypeUseCaseImpl(userTypeGateway);
    }

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
            FoodTypeGateway foodTypeGateway
    ) {
        return new DeleteFoodTypeUseCaseImpl(foodTypeGateway);
    }

    @Bean
    public UpdateFoodTypeUseCase updateFoodTypeUseCase(
            FoodTypeGateway foodTypeGateway
    ) {
        return new UpdateFoodTypeUseCaseImpl(foodTypeGateway);
    }
}
