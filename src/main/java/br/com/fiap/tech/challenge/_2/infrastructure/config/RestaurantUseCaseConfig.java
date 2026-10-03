package br.com.fiap.tech.challenge._2.infrastructure.config;

import br.com.fiap.tech.challenge._2.application.gateway.*;
import br.com.fiap.tech.challenge._2.application.usecase.restaurant.*;
import br.com.fiap.tech.challenge._2.application.usecase.restaurant.Impl.*;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.impl.GetAllUserTypeUseCaseImpl;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.impl.UpdateUserTypeUseCaseImpl;
import br.com.fiap.tech.challenge._2.infrastructure.gateway.RestaurantGatewayImpl;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.AddressRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.FoodTypeRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.RestaurantRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantUseCaseConfig {
    @Bean
    public CreateRestaurantUseCase createRestaurantUseCase(
            RestaurantGateway restaurantGateway,
            UserGateway userGateway,
            FoodTypeGateway foodTypeGateway,
            AddressGateway addressGateway
    ) {
        return new CreateRestaurantUseCaseImpl(restaurantGateway,userGateway,foodTypeGateway,addressGateway);
    }

    @Bean
    public FindRestaurantUseCase findRestaurantUseCase(
            RestaurantGateway restaurantGateway
    ) {
        return new FindRestaurantUseCaseImpl(restaurantGateway);
    }

    @Bean
    public GetAllRestaurantUseCase getAllRestaurantUseCase(
            RestaurantGateway restaurantGateway
    ) {
        return new GetAllRestaurantUseCaseImpl(restaurantGateway);
    }

    @Bean
    public DeleteRestaurantUseCase deleteRestaurantUseCase(
            RestaurantGateway restaurantGateway,
            MenuGateway menuGateway
    ) {
        return new DeleteRestaurantUseCaseImpl(restaurantGateway,menuGateway);
    }

    @Bean
    public UpdateRestaurantUseCase updateRestaurantUseCase(
            RestaurantGateway restaurantGateway,
            UserGateway userGateway,
            FoodTypeGateway foodTypeGateway,
            AddressGateway addressGateway
    ) {
        return new UpdateRestaurantUseCaseImpl(restaurantGateway,userGateway,foodTypeGateway,addressGateway);
    }

    @Bean
    public RestaurantGatewayImpl restaurantGatewayImpl(
            RestaurantRepository repository,
            UserRepository userRepository,
            FoodTypeRepository foodTypeRepository,
            AddressRepository addressRepository
    ) {
        return new RestaurantGatewayImpl(repository,userRepository,foodTypeRepository,addressRepository);
    }

}
