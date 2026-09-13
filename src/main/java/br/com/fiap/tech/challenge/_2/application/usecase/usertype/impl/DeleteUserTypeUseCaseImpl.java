package br.com.fiap.tech.challenge._2.application.usecase.usertype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.DeleteUserTypeUseCase;

import java.util.UUID;

public class DeleteUserTypeUseCaseImpl implements DeleteUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public DeleteUserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public void execute(UUID id) {
        userTypeGateway.delete(id);
    }
}
