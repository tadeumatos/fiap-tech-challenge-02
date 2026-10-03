package br.com.fiap.tech.challenge._2.application.usecase.user.impl;

import br.com.fiap.tech.challenge._2.exceptions.BusinessException;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.user.UpdateUserUseCase;
import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserRequest;

import java.util.UUID;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public UpdateUserUseCaseImpl(UserGateway userGateway,UserTypeGateway userTypeGateway) {
        this.userGateway = userGateway;
        this.userTypeGateway=userTypeGateway;
    }

    @Override
    public User execute(UUID id, UserRequest request) {

        if(validateEmail(id,request.email()))
            throw new BusinessException("Already exist email with other register, please choice other email");

        UserType userType = userTypeGateway.findById(UUID.fromString(request.userTypeId())).orElseThrow(()->  new ResourceNotFoundException("Register not found"));

        User user = User.create(UUID.randomUUID(),request.name(), request.email(),UserType.create(userType.getId() ,userType.getName(),userType.isOwner()));
        return userGateway.update(id, user);
    }

    private boolean validateEmail(UUID id,String email)
    {
        User user= userGateway.findByEmail(email);

        if(user==null)
            return false;

        return !user.getId().equals(id) && user.getEmail().equals(email);
    }
}
