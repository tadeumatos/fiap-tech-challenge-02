package br.com.fiap.tech.challenge._2.application.usecase.usertype;

import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserTypeEntity;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserTypeRequest;

import java.util.UUID;

public interface UpdateUserTypeUseCase {
    UserType execute(UUID id, UserTypeRequest request);
}
