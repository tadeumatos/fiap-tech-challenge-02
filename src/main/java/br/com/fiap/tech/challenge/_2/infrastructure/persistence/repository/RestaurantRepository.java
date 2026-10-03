package br.com.fiap.tech.challenge._2.infrastructure.persistence.repository;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, UUID> {
    boolean existsByFoodTypeEntity_Id(UUID foodTypeEntityId);
    boolean existsByUserEntity_Id(UUID userEntityId);
    boolean existsByAddressEntity_Id(UUID addressEntityId);
}
