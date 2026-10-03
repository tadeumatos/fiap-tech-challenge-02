package br.com.fiap.tech.challenge._2.application.gateway;

import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    User save(User user);
    Optional<User> findById(UUID id);
    List<User> getAll();
    void delete(UUID id);
    User update(UUID id, User user);
    boolean existEmail(String email);
    User findByEmail(String email);
    boolean existUserType(UUID id);
}
