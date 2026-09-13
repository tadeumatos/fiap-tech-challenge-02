package br.com.fiap.tech.challenge._2.application.usecase.usertype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.CreateUserTypeUseCase;
import br.com.fiap.tech.challenge._2.domain.UserType;

public class CreateUserTypeUseCaseImpl implements CreateUserTypeUseCase {
    private final UserTypeGateway userTypeGateway;

    public CreateUserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public UserType execute(String name, boolean owner) {

        UserType userType = UserType.create(name, owner);

        return userTypeGateway.save(userType);
    }
}
