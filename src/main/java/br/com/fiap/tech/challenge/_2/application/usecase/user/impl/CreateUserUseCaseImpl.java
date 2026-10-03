package br.com.fiap.tech.challenge._2.application.usecase.user.impl;

import br.com.fiap.tech.challenge._2.exceptions.BusinessException;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.user.CreateUserUseCase;
import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserRequest;

import java.util.UUID;

public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;


    public CreateUserUseCaseImpl(UserGateway userGateway,UserTypeGateway userTypeGateway) {
        this.userGateway = userGateway;
        this.userTypeGateway=userTypeGateway;
    }

    @Override
    public User execute(UserRequest request) {

        if(validateEmail(request.email()))
           throw new BusinessException("Already email with register, please choice other email");

        UserType userType = userTypeGateway.findById(UUID.fromString(request.userTypeId())).orElseThrow(()->  new ResourceNotFoundException("Register not found"));

        User user = User.create(UUID.randomUUID(),request.name(), request.email(),UserType.create(userType.getId() ,userType.getName(),userType.isOwner()));
         return userGateway.save(user);
    }

    private boolean validateEmail(String email)
    {
        return userGateway.existEmail(email);
    }
}
