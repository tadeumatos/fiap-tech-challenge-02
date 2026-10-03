package br.com.fiap.tech.challenge._2.infrastructure.gateway;

import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge._2.domain.Address;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.AddressEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.AddressRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AddressGatewayImpl implements AddressGateway {
    private final AddressRepository repository;

    public AddressGatewayImpl(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Address save(Address address) {

        AddressEntity entity = new AddressEntity(
                address.getName(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry()
        );

       var saved= repository.save(entity);
       return toDomain(saved);

    }

    @Override
    public Optional<Address> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);

    }

    @Override
    public List<Address> getAll() {
        var list= repository.findAll();
        return toDomain(list);
    }

    @Override
    public void delete(UUID id) {
        var entity = repository.findById(id);

        if(entity.isEmpty())
          throw new ResourceNotFoundException("Register not found") ;

        repository.deleteById(entity.get().getId());
    }

    @Override
    public Address update(UUID id, Address address) {
        var entity = repository.findById(id).orElseThrow(()->  new ResourceNotFoundException("Register not found"));

        entity.setName(address.getName());
        entity.setNeighborhood(address.getNeighborhood());
        entity.setCity(address.getCity());
        entity.setState(address.getState());
        entity.setPostalCode(address.getPostalCode());
        entity.setCountry(address.getCountry());

        var updated= repository.save(entity);

        return toDomain(updated);
    }

    @Override
    public Address findByPostalCode(String postalCode) {
        var addressEntity= repository.findByPostalCode(postalCode);

        if(addressEntity==null) {
            return null;
        }

        return toDomain(addressEntity);
    }

    @Override
    public boolean existPostalCode(String postalCode) {
        return repository.existsByPostalCode(postalCode);
    }

    private Address toDomain(AddressEntity entity) {

        return new Address(
                entity.getId(),
                entity.getName(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                entity.getPostalCode(),
                entity.getCountry()
        );

    }

    private List<Address> toDomain(List<AddressEntity> list) {
        List<Address> addressList=  new ArrayList<>();

        for (AddressEntity addressEntity : list) {
            addressList.add( new Address(addressEntity.getId(),addressEntity.getName(),addressEntity.getNeighborhood(),addressEntity.getCity(), addressEntity.getState(), addressEntity.getPostalCode(), addressEntity.getCountry()));
        }
        return addressList;
    }
}
