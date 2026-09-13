package br.com.fiap.tech.challenge._2.application.usecase.usertype;

import br.com.fiap.tech.challenge._2.domain.UserType;

import java.util.List;


public interface GetAllUserTypeUseCase {

    List<UserType> execute();
}
