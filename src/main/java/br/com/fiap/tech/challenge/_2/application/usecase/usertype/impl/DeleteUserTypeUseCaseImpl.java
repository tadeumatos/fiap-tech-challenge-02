package br.com.fiap.tech.challenge._2.application.usecase.usertype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.DeleteUserTypeUseCase;
import br.com.fiap.tech.challenge._2.exceptions.EntityInUseException;

import java.util.UUID;

public class DeleteUserTypeUseCaseImpl implements DeleteUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;
    private final UserGateway userGateway;

    public DeleteUserTypeUseCaseImpl(UserTypeGateway userTypeGateway,UserGateway userGateway) {
        this.userTypeGateway = userTypeGateway;
        this.userGateway = userGateway;
    }

    @Override
    public void execute(UUID id) {

        if(deleteUserTypeValidation(id)){
            throw new EntityInUseException("The cannot be deleted, the register this in use");
        }

        userTypeGateway.delete(id);
    }

    private boolean deleteUserTypeValidation(UUID id)
    {
        return userGateway.existUserType(id);
    }
}
