package br.com.fiap.tech.challenge._2.presentation.gateway;

import br.com.fiap.tech.challenge._2.presentation.controller.request.CreateUserTypeRequest;
import br.com.fiap.tech.challenge._2.application.exceptions.NotFoundException;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entity.UserTypeJpaEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserTypeRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserTypeGatewayImpl implements UserTypeGateway {
    private final UserTypeRepository repository;

    public UserTypeGatewayImpl(UserTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserType save(UserType userType) {
        UserTypeJpaEntity entity = new UserTypeJpaEntity(
                userType.getId(),
                userType.getName(),
                userType.isOwner()
        );

        UserTypeJpaEntity saved = repository.save(entity);

        return toDomain(saved);
    }

    @Override
    public Optional<UserType> findById(UUID id) {
        return repository.findById(id)
                .map(this::toDomain);
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
          throw new NotFoundException("Register not found") ;

        repository.deleteById(entity.get().getId());
    }

    @Override
    public UserType update(UUID id, CreateUserTypeRequest createUserTypeRequest ) {
        var userTypeJpaEntity = repository.findById(id);

        if (userTypeJpaEntity.isEmpty())
            throw new NotFoundException("Register not found") ;

        UserTypeJpaEntity entity = new UserTypeJpaEntity(
                userTypeJpaEntity.get().getId(),
                createUserTypeRequest.name(),
                createUserTypeRequest.owner()
        );
        UserTypeJpaEntity updated = repository.save(entity);

        return toDomain(updated);
    }

    private UserType toDomain(UserTypeJpaEntity entity) {

        return new UserType(
                entity.getId(),
                entity.getName(),
                entity.isOwner()
        );
    }

    private List<UserType> toDomain(List<UserTypeJpaEntity> list) {
        List<UserType> userTypeList=  new ArrayList<>();

        for (UserTypeJpaEntity userTypeJpaEntity : list) {
            userTypeList.add( new UserType(userTypeJpaEntity.getId(),userTypeJpaEntity.getName(),userTypeJpaEntity.isOwner()));
        }
        return userTypeList;

    }
}
