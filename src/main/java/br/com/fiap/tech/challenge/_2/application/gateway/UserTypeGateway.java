package br.com.fiap.tech.challenge._2.application.gateway;

import br.com.fiap.tech.challenge._2.presentation.controller.request.CreateUserTypeRequest;
import br.com.fiap.tech.challenge._2.domain.UserType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTypeGateway {
    UserType save(UserType userType);
    Optional<UserType> findById(UUID id);
    List<UserType> getAll();
    void delete(UUID id);
    UserType update(UUID id, CreateUserTypeRequest createUserTypeRequest);
}
