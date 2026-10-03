package br.com.fiap.tech.challenge._2.infrastructure.gateway;


import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.domain.*;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.RestaurantEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.AddressRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.FoodTypeRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.RestaurantRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserRepository;
import br.com.fiap.tech.challenge._2.presentation.controller.response.AddressResponse;


import java.util.*;


public class RestaurantGatewayImpl implements RestaurantGateway {
    private final RestaurantRepository repository;
    private final UserRepository userRepository;
    private final FoodTypeRepository foodTypeRepository;
    private final AddressRepository addressRepository;

    public RestaurantGatewayImpl(RestaurantRepository repository,
                                 UserRepository userRepository,
                                 FoodTypeRepository foodTypeRepository,
                                 AddressRepository addressRepository) {
        this.repository = repository;
        this.userRepository=userRepository;
        this.foodTypeRepository=foodTypeRepository;
        this.addressRepository=addressRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        var userEntity = userRepository.findById(restaurant.getUser().getId()).orElseThrow(()-> new ResourceNotFoundException("The user not found"));
        var foodTypeEntity = foodTypeRepository.findById(restaurant.getFoodType().getId()).orElseThrow(()-> new ResourceNotFoundException("The food type not found"));
        var addressEntity = addressRepository.findById(restaurant.getAddress().getId()).orElseThrow(()-> new ResourceNotFoundException("The address not found"));

        RestaurantEntity entity = new RestaurantEntity(
          restaurant.getName(),
          restaurant.getDescription(),
          addressEntity,
          restaurant.getAddressNumber(),
          restaurant.getAddressComplement(),
          foodTypeEntity,
          userEntity,
          restaurant.getStartTime(),
          restaurant.getEndTime()
        );

        var saved= repository.save(entity);

        return toDomain(saved);
    }

    @Override
    public Optional<Restaurant> findById(UUID id) {

        return repository.findById(id)
                  .map(this::toDomain);
    }

    @Override
    public List<Restaurant> getAll() {
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
    public Restaurant update(UUID id, Restaurant restaurant) {
        var entity = repository.findById(id).orElseThrow(()->  new ResourceNotFoundException("The register not found"));
        var userEntity = userRepository.findById(restaurant.getUser().getId()).orElseThrow(()-> new ResourceNotFoundException("The user not found"));
        var foodTypeEntity = foodTypeRepository.findById(restaurant.getFoodType().getId()).orElseThrow(()-> new ResourceNotFoundException("The food type not found"));
        var addressEntity = addressRepository.findById(restaurant.getAddress().getId()).orElseThrow(()-> new ResourceNotFoundException("The address not found"));

        entity.setName(restaurant.getName());
        entity.setDescription(restaurant.getDescription());
        entity.setAddressEntity(addressEntity);
        entity.setAddressNumber(restaurant.getAddressNumber());
        entity.setAddressComplement(restaurant.getAddressComplement());
        entity.setUserEntity(userEntity);
        entity.setFoodTypeEntity(foodTypeEntity);
        entity.setStartTime(restaurant.getStartTime());
        entity.setEndTime(restaurant.getEndTime());

        var updated= repository.save(entity);

        return toDomain(updated);
    }

    @Override
    public boolean existFoodType(UUID id) {
        return repository.existsByFoodTypeEntity_Id(id);
    }

    @Override
    public boolean existUser(UUID id) {
        return repository.existsByUserEntity_Id(id);
    }

    @Override
    public boolean existAddress(UUID id) {
        return repository.existsByAddressEntity_Id(id);
    }

    private Restaurant toDomain(RestaurantEntity entity) {

        return new Restaurant(
           entity.getId(),
           entity.getName(),
           entity.getDescription(),
                new Address(
                        entity.getAddressEntity().getId(),
                        entity.getAddressEntity().getName(),
                        entity.getAddressEntity().getNeighborhood(),
                        entity.getAddressEntity().getCity(),
                        entity.getAddressEntity().getState(),
                        entity.getAddressEntity().getPostalCode(),
                        entity.getAddressEntity().getCountry()
                ),
                entity.getAddressNumber(),
                entity.getAddressComplement(),
           new User(entity.getUserEntity().getId(),
                    entity.getUserEntity().getName(),
                    entity.getUserEntity().getEmail(),
                    new UserType(entity.getUserEntity().getUserTypeEntity().getId(),
                                 entity.getUserEntity().getUserTypeEntity().getName(),
                                 entity.getUserEntity().getUserTypeEntity().isOwner())
                     ),
           new FoodType(entity.getFoodTypeEntity().getId(),
                        entity.getFoodTypeEntity().getName()),
           entity.getStartTime(),
           entity.getEndTime()
        );
    }

    private List<Restaurant> toDomain(List<RestaurantEntity> list) {
        List<Restaurant> restaurantList=  new ArrayList<>();

        for (RestaurantEntity restaurantEntity : list) {
            restaurantList.add(
                    new Restaurant(
                       restaurantEntity.getId(),
                       restaurantEntity.getName(),
                       restaurantEntity.getDescription(),
                            new Address(
                                    restaurantEntity.getAddressEntity().getId(),
                                    restaurantEntity.getAddressEntity().getName(),
                                    restaurantEntity.getAddressEntity().getNeighborhood(),
                                    restaurantEntity.getAddressEntity().getCity(),
                                    restaurantEntity.getAddressEntity().getState(),
                                    restaurantEntity.getAddressEntity().getPostalCode(),
                                    restaurantEntity.getAddressEntity().getCountry()
                            ),
                       restaurantEntity.getAddressNumber(),
                       restaurantEntity.getAddressComplement(),
                            new User(restaurantEntity.getUserEntity().getId(),
                               restaurantEntity.getUserEntity().getName(),
                               restaurantEntity.getUserEntity().getEmail(),
                                new UserType(restaurantEntity.getUserEntity().getUserTypeEntity().getId(),
                                             restaurantEntity.getUserEntity().getUserTypeEntity().getName(),
                                             restaurantEntity.getUserEntity().getUserTypeEntity().isOwner())
                            ),
                            new FoodType(restaurantEntity.getFoodTypeEntity().getId(),
                                         restaurantEntity.getFoodTypeEntity().getName()),
                       restaurantEntity.getStartTime(),
                       restaurantEntity.getEndTime()
                    )
            );
        }
        return restaurantList;
    }
}
