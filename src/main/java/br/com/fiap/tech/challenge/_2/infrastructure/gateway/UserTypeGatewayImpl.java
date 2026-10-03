package br.com.fiap.tech.challenge._2.infrastructure.gateway;

import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserTypeEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserTypeGatewayImpl implements UserTypeGateway {
    private final UserTypeRepository repository;

    public UserTypeGatewayImpl(UserTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserType save(UserType userType) {

        UserTypeEntity entity = new UserTypeEntity(
                userType.getName(),
                userType.isOwner()
        );

       var saved= repository.save(entity);
       return toDomain(saved);

    }

    @Override
    public Optional<UserType> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);

    }

    @Override
    public List<UserType> getAll() {
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
    public UserType update(UUID id, UserType userType) {
        var entity = repository.findById(id).orElseThrow(()->  new ResourceNotFoundException("Register not found"));

        entity.setName(userType.getName());
        entity.setOwner(userType.isOwner());

        var updated= repository.save(entity);

        return toDomain(updated);
    }

    private UserType toDomain(UserTypeEntity entity) {

        return new UserType(
                entity.getId(),
                entity.getName(),
                entity.isOwner()
        );

    }

    private List<UserType> toDomain(List<UserTypeEntity> list) {
        List<UserType> userTypeList=  new ArrayList<>();

        for (UserTypeEntity userTypeEntity : list) {
            userTypeList.add( new UserType(userTypeEntity.getId(),userTypeEntity.getName(),userTypeEntity.isOwner()));
        }
        return userTypeList;
    }
}
