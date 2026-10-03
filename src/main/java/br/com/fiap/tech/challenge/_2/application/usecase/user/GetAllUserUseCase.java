package br.com.fiap.tech.challenge._2.application.usecase.user;

import br.com.fiap.tech.challenge._2.domain.User;
import java.util.List;


public interface GetAllUserUseCase {
    List<User> execute();
}
