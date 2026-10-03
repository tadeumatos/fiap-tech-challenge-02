package br.com.fiap.tech.challenge._2.application.usecase.user;

import java.util.UUID;

public interface DeleteUserUseCase {
    void execute(UUID id);
}
