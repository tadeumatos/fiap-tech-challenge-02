package br.com.fiap.tech.challenge._2.application.usecase.usertype;

import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserTypeEntity;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserTypeRequest;

public interface CreateUserTypeUseCase {
    UserType execute(UserTypeRequest request);
}
