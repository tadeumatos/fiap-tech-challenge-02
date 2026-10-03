package br.com.fiap.tech.challenge._2.application.usecase.restaurant.Impl;

import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.application.usecase.restaurant.CreateRestaurantUseCase;
import br.com.fiap.tech.challenge._2.domain.Address;
import br.com.fiap.tech.challenge._2.domain.FoodType;
import br.com.fiap.tech.challenge._2.domain.Restaurant;
import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.presentation.controller.request.RestaurantRequest;

import java.util.UUID;

public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;
    private final FoodTypeGateway foodTypeGateway;
    private final AddressGateway addressGateway;


    public CreateRestaurantUseCaseImpl(RestaurantGateway restaurantGateway, UserGateway userGateway, FoodTypeGateway foodTypeGateway,AddressGateway addressGateway) {
        this.restaurantGateway=restaurantGateway;
        this.userGateway = userGateway;
        this.foodTypeGateway=foodTypeGateway;
        this.addressGateway=addressGateway;

    }

    @Override
    public Restaurant execute(RestaurantRequest request) {
        User user = userGateway.findById(UUID.fromString(request.userId())).orElseThrow(()->  new ResourceNotFoundException("The user not found"));
        FoodType foodType = foodTypeGateway.findById(UUID.fromString(request.foodTypeId())).orElseThrow(()->  new ResourceNotFoundException("The food type not found"));
        Address address = addressGateway.findById(UUID.fromString(request.addressId())).orElseThrow(()->  new ResourceNotFoundException("The address not found"));

        Restaurant restaurant = Restaurant.create(UUID.randomUUID(),request.name(),request.description(),address, request.addressNumber(), request.addressComplement(),user,foodType,request.startTime(),request.endTime() );
        return restaurantGateway.save(restaurant);
    }
}
