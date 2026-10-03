package br.com.fiap.tech.challenge._2.application.usecase.user;

import br.com.fiap.tech.challenge._2.domain.User;
import java.util.UUID;


public interface FindUserUseCase {
    User execute(UUID id);
}
