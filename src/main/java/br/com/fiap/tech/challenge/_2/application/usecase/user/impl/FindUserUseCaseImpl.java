package br.com.fiap.tech.challenge._2.application.usecase.user.impl;

import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.application.usecase.user.FindUserUseCase;
import br.com.fiap.tech.challenge._2.domain.User;

import java.util.UUID;

public class FindUserUseCaseImpl implements FindUserUseCase {
    private final UserGateway userGateway;

    public FindUserUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;

    }

    @Override
    public User execute(UUID id) {

        return userGateway.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("The user not found"));
    }
}
