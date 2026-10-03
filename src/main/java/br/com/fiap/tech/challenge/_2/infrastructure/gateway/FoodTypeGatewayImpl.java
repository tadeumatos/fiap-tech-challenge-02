package br.com.fiap.tech.challenge._2.infrastructure.gateway;

import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.domain.FoodType;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.FoodTypeEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.FoodTypeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class FoodTypeGatewayImpl implements FoodTypeGateway {
    private final FoodTypeRepository repository;

    public FoodTypeGatewayImpl(FoodTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public FoodType save(FoodType foodType) {
        FoodTypeEntity entity = new FoodTypeEntity(
               foodType.getName()
        );

        var saved= repository.save(entity);

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
            throw new ResourceNotFoundException("Register not found") ;

        repository.deleteById(entity.get().getId());
    }

    @Override
    public FoodType update(UUID id, FoodType foodType) {
        var entity = repository.findById(id).orElseThrow(()->  new ResourceNotFoundException("Register not found"));
        entity.setName(foodType.getName());

        var updated= repository.save(entity);

        return toDomain(updated);
    }

    private FoodType toDomain(FoodTypeEntity entity) {

        return new FoodType(
                entity.getId(),
                entity.getName()
        );
    }

    private List<FoodType> toDomain(List<FoodTypeEntity> list) {
        List<FoodType> foodTypeList=  new ArrayList<>();

        for (FoodTypeEntity foodTypeEntity : list) {
            foodTypeList.add( new FoodType(foodTypeEntity.getId(), foodTypeEntity.getName()));
        }
        return foodTypeList;
    }
}
