package br.com.fiap.tech.challenge._2.application.usecase.usertype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.GetAllUserTypeUseCase;
import br.com.fiap.tech.challenge._2.domain.UserType;

import java.util.List;

public class GetAllUserTypeUseCaseImpl implements GetAllUserTypeUseCase {
    private final UserTypeGateway userTypeGateway;

    public GetAllUserTypeUseCaseImpl(UserTypeGateway userTypeGateway)
    {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public List<UserType> execute() {
        return userTypeGateway.getAll();
    }
}
