package br.com.fiap.tech.challenge._2.application.usecase.user;

import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserRequest;


public interface CreateUserUseCase {
    User execute(UserRequest request);
}
