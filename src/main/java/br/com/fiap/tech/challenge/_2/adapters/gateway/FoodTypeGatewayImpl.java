package br.com.fiap.tech.challenge._2.adapters.gateway;

import br.com.fiap.tech.challenge._2.adapters.controller.request.CreateFoodTypeRequest;
import br.com.fiap.tech.challenge._2.application.exceptions.NotFoundException;
import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.domain.FoodType;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entity.FoodTypeJpaEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.FoodTypeRepository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FoodTypeGatewayImpl implements FoodTypeGateway {
    private final FoodTypeRepository repository;

    public FoodTypeGatewayImpl(FoodTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public FoodType save(FoodType foodType) {
        FoodTypeJpaEntity entity = new FoodTypeJpaEntity(
                foodType.getId(),
                foodType.getName()
        );

        FoodTypeJpaEntity saved = repository.save(entity);

        return toDomain(saved);
    }

    @Override
    public Optional<FoodType> findById(UUID id) {
        return repository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<FoodType> getAll() {
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
    public FoodType update(UUID id, CreateFoodTypeRequest createFoodTypeRequest) {
        var foodTypeJpaEntity = repository.findById(id);

        if (foodTypeJpaEntity.isEmpty())
            throw new NotFoundException("Register not found") ;

        FoodTypeJpaEntity entity = new FoodTypeJpaEntity(
                foodTypeJpaEntity.get().getId(),
                createFoodTypeRequest.name()
        );
        FoodTypeJpaEntity updated = repository.save(entity);

        return toDomain(updated);
    }

    private FoodType toDomain(FoodTypeJpaEntity entity) {

        return new FoodType(
                entity.getId(),
                entity.getName()
        );
    }

    private List<FoodType> toDomain(List<FoodTypeJpaEntity> list) {
        List<FoodType> foodTypeList=  new ArrayList<>();

        for (FoodTypeJpaEntity foodTypeJpaEntity : list) {
            foodTypeList.add( new FoodType(foodTypeJpaEntity.getId(),foodTypeJpaEntity.getName()));
        }
        return foodTypeList;

    }
}
