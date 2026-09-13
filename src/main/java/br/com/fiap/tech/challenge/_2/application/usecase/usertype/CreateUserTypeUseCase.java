package br.com.fiap.tech.challenge._2.application.usecase.usertype;

import br.com.fiap.tech.challenge._2.domain.UserType;

public interface CreateUserTypeUseCase {
    UserType execute(String name, boolean owner);
}
