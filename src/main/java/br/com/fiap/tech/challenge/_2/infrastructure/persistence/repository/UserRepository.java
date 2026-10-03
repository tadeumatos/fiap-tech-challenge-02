package br.com.fiap.tech.challenge._2.infrastructure.persistence.repository;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
    boolean existsByUserTypeEntity_Id(UUID userTypeEntityId);
}
