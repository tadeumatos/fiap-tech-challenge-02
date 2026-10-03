package br.com.fiap.tech.challenge._2.infrastructure.config;

import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.*;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.impl.*;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserTypeRepository;
import br.com.fiap.tech.challenge._2.infrastructure.gateway.UserTypeGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeUseCaseConfig {
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
            UserTypeGateway userTypeGateway,
            UserGateway userGateway
    ) {
        return new DeleteUserTypeUseCaseImpl(userTypeGateway,userGateway);
    }

    @Bean
    public UpdateUserTypeUseCase updateUserTypeUseCase(
            UserTypeGateway userTypeGateway
    ) {
        return new UpdateUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public UserTypeGatewayImpl userTypeGatewayImpl(
            UserTypeRepository userTypeRepository
    ) {
        return new UserTypeGatewayImpl(userTypeRepository);
    }

}
