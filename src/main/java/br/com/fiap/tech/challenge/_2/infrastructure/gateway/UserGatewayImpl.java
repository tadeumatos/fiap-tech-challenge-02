package br.com.fiap.tech.challenge._2.infrastructure.gateway;

import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserGatewayImpl implements UserGateway {
    private final UserRepository repository;
    private final UserTypeRepository userTypeRepository;
    public UserGatewayImpl(UserRepository repository, UserTypeRepository userTypeRepository) {
        this.repository = repository;
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public User save(User user) {
       var userTypeEntity = userTypeRepository.findById(user.getUserType().getId()).orElseThrow(()-> new ResourceNotFoundException("Register nor found"));

        UserEntity entity = new UserEntity(
                user.getName(),
                user.getEmail(),
                userTypeEntity
                );

        var saved= repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        var userEntity= repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Register not found"));

        return Optional.of(toDomain(userEntity));

    }

    @Override
    public List<User> getAll() {
        var list= repository.findAll();
        return toDomain(list);
    }

    @Override
    public void delete(UUID id) {
        var entity = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Register not found"));

        repository.deleteById(entity.getId());
    }

    @Override
    public User update(UUID id, User user) {
        var entity = repository.findById(id).orElseThrow(()->  new ResourceNotFoundException("Register not found"));
        var userTypeEntity = userTypeRepository.findById(user.getUserType().getId()).orElseThrow(()-> new ResourceNotFoundException("Register not found"));

        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setUserTypeEntity(userTypeEntity);

        var updated= repository.save(entity);
        return toDomain(updated);
    }

    @Override
    public boolean existEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        var userEntity= repository.findByEmail(email);

        if (userEntity==null)
            return null;

        return toDomain(userEntity);
    }

    @Override
    public boolean existUserType(UUID id) {
        return repository.existsByUserTypeEntity_Id(id);
    }

    private User toDomain(UserEntity entity) {

        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                new UserType(entity.getUserTypeEntity().getId(),
                        entity.getUserTypeEntity().getName(),
                        entity.getUserTypeEntity().isOwner())
        );
    }

    private List<User> toDomain(List<UserEntity> list) {
        List<User> userList = new ArrayList<>();

        for (UserEntity userEntity : list) {
            userList.add( new User( userEntity.getId(),userEntity.getName(), userEntity.getEmail(),
                    new UserType(userEntity.getUserTypeEntity().getId(),userEntity.getUserTypeEntity().getName(),userEntity.getUserTypeEntity().isOwner())));

        }
        return userList;
    }
}
