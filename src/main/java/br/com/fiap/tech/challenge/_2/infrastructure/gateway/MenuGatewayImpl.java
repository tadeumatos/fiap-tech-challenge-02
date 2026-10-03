package br.com.fiap.tech.challenge._2.infrastructure.gateway;

import br.com.fiap.tech.challenge._2.application.gateway.MenuGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.domain.*;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.MenuEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.RestaurantEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.MenuRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.RestaurantRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MenuGatewayImpl implements MenuGateway {
    private final MenuRepository repository;
    private final RestaurantRepository restaurantRepository;

    public MenuGatewayImpl(MenuRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Menu save(Menu menu) {
       var restaurantEntity = restaurantRepository.findById(menu.getRestaurant().getId()).orElseThrow(()-> new ResourceNotFoundException("Register nor found"));

        MenuEntity entity = new MenuEntity(
                menu.getName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.isOnlyLocal(),
                menu.getFoodPhoto(),
                restaurantEntity,
                menu.isActive()
                );

        var saved= repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        var menuEntity= repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("The menu not found"));

        return Optional.of(toDomain(menuEntity));

    }

    @Override
    public List<Menu> getAll() {
        var list= repository.findAll();
        return toDomain(list);
    }

    @Override
    public void delete(UUID id) {
        var entity = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("The menu not found"));

        repository.deleteById(entity.getId());
    }

    @Override
    public Menu update(UUID id, Menu menu) {
        var restaurantEntity = restaurantRepository.findById(menu.getRestaurant().getId()).orElseThrow(()-> new ResourceNotFoundException("Register not found"));
        var entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("The menu not found"));

        entity.setName(menu.getName());
        entity.setDescription(menu.getDescription());
        entity.setPrice(menu.getPrice());
        entity.setOnlyLocal(menu.isOnlyLocal());
        entity.setFoodPhoto(menu.getFoodPhoto());
        entity.setRestaurantEntity(restaurantEntity);
        entity.setActive(menu.isActive());

        var updated= repository.save(entity);
        return toDomain(updated);
    }

    @Override
    public boolean existRestaurant(UUID id) {
        return repository.existsByRestaurantEntity_Id(id);
    }


    private Menu toDomain(MenuEntity entity) {

        return new Menu(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.isOnlyLocal(),
                entity.getFoodPhoto(),
                new Restaurant(entity.getRestaurantEntity().getId(),
                               entity.getRestaurantEntity().getName(),
                               entity.getRestaurantEntity().getDescription(),
                               new Address(
                                entity.getRestaurantEntity().getAddressEntity().getId(),
                                entity.getRestaurantEntity().getAddressEntity().getName(),
                                entity.getRestaurantEntity().getAddressEntity().getNeighborhood(),
                                entity.getRestaurantEntity().getAddressEntity().getCity(),
                                entity.getRestaurantEntity().getAddressEntity().getState(),
                                entity.getRestaurantEntity().getAddressEntity().getPostalCode(),
                                entity.getRestaurantEntity().getAddressEntity().getCountry()
                                ),
                                entity.getRestaurantEntity().getAddressNumber(),
                                entity.getRestaurantEntity().getAddressComplement(),
                               new User(entity.getRestaurantEntity().getUserEntity().getId(),
                                       entity.getRestaurantEntity().getUserEntity().getName(),
                                       entity.getRestaurantEntity().getUserEntity().getEmail(),
                                       new UserType(
                                               entity.getRestaurantEntity().getUserEntity().getUserTypeEntity().getId(),
                                               entity.getRestaurantEntity().getUserEntity().getUserTypeEntity().getName(),
                                               entity.getRestaurantEntity().getUserEntity().getUserTypeEntity().isOwner()
                                       )
                               ),
                              new FoodType(entity.getRestaurantEntity().getFoodTypeEntity().getId(),
                                      entity.getRestaurantEntity().getFoodTypeEntity().getName()),
                              entity.getRestaurantEntity().getStartTime(),
                              entity.getRestaurantEntity().getEndTime()

               ),
                entity.isActive()
        );
    }

    private List<Menu> toDomain(List<MenuEntity> list) {
        List<Menu> menuList = new ArrayList<>();

        for (MenuEntity menuEntity : list) {
            menuList.add(
                    new Menu(
                            menuEntity.getId(),
                            menuEntity.getName(),
                            menuEntity.getDescription(),
                            menuEntity.getPrice(),
                            menuEntity.isOnlyLocal(),
                            menuEntity.getFoodPhoto(),
                            new Restaurant(menuEntity.getRestaurantEntity().getId(),
                                    menuEntity.getRestaurantEntity().getName(),
                                    menuEntity.getRestaurantEntity().getDescription(),
                                    new Address(
                                            menuEntity.getRestaurantEntity().getAddressEntity().getId(),
                                            menuEntity.getRestaurantEntity().getAddressEntity().getName(),
                                            menuEntity.getRestaurantEntity().getAddressEntity().getNeighborhood(),
                                            menuEntity.getRestaurantEntity().getAddressEntity().getCity(),
                                            menuEntity.getRestaurantEntity().getAddressEntity().getState(),
                                            menuEntity.getRestaurantEntity().getAddressEntity().getPostalCode(),
                                            menuEntity.getRestaurantEntity().getAddressEntity().getCountry()
                                    ),
                                    menuEntity.getRestaurantEntity().getAddressNumber(),
                                    menuEntity.getRestaurantEntity().getAddressComplement(),

                                    new User(menuEntity.getRestaurantEntity().getUserEntity().getId(),
                                            menuEntity.getRestaurantEntity().getUserEntity().getName(),
                                            menuEntity.getRestaurantEntity().getUserEntity().getEmail(),
                                            new UserType(
                                                    menuEntity.getRestaurantEntity().getUserEntity().getUserTypeEntity().getId(),
                                                    menuEntity.getRestaurantEntity().getUserEntity().getUserTypeEntity().getName(),
                                                    menuEntity.getRestaurantEntity().getUserEntity().getUserTypeEntity().isOwner()
                                            )
                                    ),
                                    new FoodType(menuEntity.getRestaurantEntity().getFoodTypeEntity().getId(),
                                            menuEntity.getRestaurantEntity().getFoodTypeEntity().getName()),
                                    menuEntity.getRestaurantEntity().getStartTime(),
                                    menuEntity.getRestaurantEntity().getEndTime()

                            ),
                            menuEntity.isActive()
                    )

            );

        }
        return menuList;
    }
}
