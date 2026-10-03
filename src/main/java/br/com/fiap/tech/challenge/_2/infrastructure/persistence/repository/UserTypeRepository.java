package br.com.fiap.tech.challenge._2.infrastructure.persistence.repository;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserTypeRepository extends JpaRepository<UserTypeEntity, UUID> {
}
