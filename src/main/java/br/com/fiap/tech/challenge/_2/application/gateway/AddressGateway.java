package br.com.fiap.tech.challenge._2.application.gateway;

import br.com.fiap.tech.challenge._2.domain.Address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressGateway {
    Address save(Address address);
    Optional<Address> findById(UUID id);
    List<Address> getAll();
    void delete(UUID id);
    Address update(UUID id, Address address);
    Address findByPostalCode(String postalCode);
    boolean existPostalCode(String postalCode);
}
