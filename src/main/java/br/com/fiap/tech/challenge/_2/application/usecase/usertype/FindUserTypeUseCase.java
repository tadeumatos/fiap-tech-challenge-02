package br.com.fiap.tech.challenge._2.application.usecase.usertype;

import br.com.fiap.tech.challenge._2.domain.UserType;

import java.util.UUID;

public interface FindUserTypeUseCase {
    UserType execute(UUID id);
}
