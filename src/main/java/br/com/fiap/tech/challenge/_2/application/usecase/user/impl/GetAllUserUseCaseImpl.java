package br.com.fiap.tech.challenge._2.application.usecase.user.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.application.usecase.user.GetAllUserUseCase;
import br.com.fiap.tech.challenge._2.domain.User;

import java.util.List;

public class GetAllUserUseCaseImpl implements GetAllUserUseCase {
    private final UserGateway userGateway;

    public GetAllUserUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public List<User> execute() {

        return userGateway.getAll();
    }
}
