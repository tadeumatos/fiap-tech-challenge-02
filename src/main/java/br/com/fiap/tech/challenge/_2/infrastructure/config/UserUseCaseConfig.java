package br.com.fiap.tech.challenge._2.infrastructure.config;

import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.user.*;
import br.com.fiap.tech.challenge._2.application.usecase.user.impl.*;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.*;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.impl.*;
import br.com.fiap.tech.challenge._2.infrastructure.gateway.UserGatewayImpl;
import br.com.fiap.tech.challenge._2.infrastructure.gateway.UserTypeGatewayImpl;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserTypeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfig {
    @Bean
    public CreateUserUseCase createUserUseCase(
            UserGateway userGateway,
            UserTypeGateway userTypeGateway

    ) {
        return new CreateUserUseCaseImpl(userGateway,userTypeGateway);
    }

    @Bean
    public UserGatewayImpl userGatewayImpl(
            UserRepository userRepository,
            UserTypeRepository userTypeRepository
    ) {
        return new UserGatewayImpl(userRepository,userTypeRepository);
    }

    @Bean
    public FindUserUseCase findUserUseCase(
            UserGateway userGateway
    ) {
        return new FindUserUseCaseImpl(userGateway);
    }

    @Bean
    public GetAllUserUseCase getAllUserUseCase(
            UserGateway userGateway
    ) {
        return new GetAllUserUseCaseImpl(userGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(
            UserGateway userGateway,
            RestaurantGateway restaurantGateway
    ) {
        return new DeleteUserUseCaseImpl(userGateway,restaurantGateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(
            UserGateway userGateway,
            UserTypeGateway userTypeGateway
    ) {
        return new UpdateUserUseCaseImpl(userGateway,userTypeGateway);
    }


}
