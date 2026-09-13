package br.com.fiap.tech.challenge._2.application.usecase.usertype;

import br.com.fiap.tech.challenge._2.adapters.controller.request.CreateUserTypeRequest;
import br.com.fiap.tech.challenge._2.domain.UserType;

import java.util.UUID;

public interface UpdateUserTypeUseCase {
    UserType execute(UUID id, CreateUserTypeRequest createUserTypeRequest);
}
