package br.com.fiap.tech.challenge._2.application.usecase.usertype;

import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserTypeEntity;

import java.util.List;


public interface GetAllUserTypeUseCase {

    List<UserType> execute();
}
