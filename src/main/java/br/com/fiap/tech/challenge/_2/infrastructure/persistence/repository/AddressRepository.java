package br.com.fiap.tech.challenge._2.infrastructure.persistence.repository;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
    AddressEntity findByPostalCode(String postalCode);

    boolean existsByPostalCode(String postalCode);
}
