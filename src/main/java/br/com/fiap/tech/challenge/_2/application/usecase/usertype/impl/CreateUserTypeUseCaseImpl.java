package br.com.fiap.tech.challenge._2.application.usecase.usertype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.CreateUserTypeUseCase;
import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserTypeRequest;
import java.util.UUID;

public class CreateUserTypeUseCaseImpl implements CreateUserTypeUseCase {
    private final UserTypeGateway userTypeGateway;

    public CreateUserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public UserType execute(UserTypeRequest request) {

        UserType userType = UserType.create(UUID.randomUUID(),request.name(), request.owner());

        return userTypeGateway.save(userType);
    }
}
