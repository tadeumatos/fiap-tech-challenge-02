package br.com.fiap.tech.challenge._2.application.usecase.usertype.impl;

import br.com.fiap.tech.challenge._2.application.exceptions.NotFoundException;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.FindUserTypeUseCase;
import br.com.fiap.tech.challenge._2.domain.UserType;

import java.util.UUID;

public class FindUserTypeUseCaseImpl implements FindUserTypeUseCase {
    private final UserTypeGateway userTypeGateway;

    public FindUserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public UserType execute(UUID id) {

        return userTypeGateway.findById(id)
                .orElseThrow(() ->
                        new  NotFoundException("User Type not found"));
    }
}
