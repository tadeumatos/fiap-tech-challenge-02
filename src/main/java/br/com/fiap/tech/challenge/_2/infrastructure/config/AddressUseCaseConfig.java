package br.com.fiap.tech.challenge._2.infrastructure.config;

import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.address.*;
import br.com.fiap.tech.challenge._2.application.usecase.address.impl.*;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.impl.UpdateUserTypeUseCaseImpl;
import br.com.fiap.tech.challenge._2.infrastructure.gateway.AddressGatewayImpl;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.AddressRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressUseCaseConfig {
    @Bean
    public CreateAddressUseCase createAddressUseCase(
            AddressGateway addressGateway
    ) {
        return new CreateAddressUseCaseImpl(addressGateway);
    }

    @Bean
    public FindAddressUseCase findAddressUseCase(
            AddressGateway addressGateway
    ) {
        return new FindAddressUseCaseImpl(addressGateway);
    }

    @Bean
    public GetAllAddressUseCase getAllAddressUseCase(
            AddressGateway addressGateway
    ) {
        return new GetAllAddressUseCaseImpl(addressGateway);
    }

    @Bean
    public DeleteAddressUseCase deleteAddressUseCase(
            AddressGateway addressGateway,
            RestaurantGateway restaurantGateway
    ) {
        return new DeleteAddressUseCaseImpl(addressGateway,restaurantGateway);
    }

    @Bean
    public UpdateAddressUseCase updateAddressUseCase(
            AddressGateway addressGateway
    ) {
        return new UpdateAddressUseCaseImpl(addressGateway);
    }

    @Bean
    public AddressGatewayImpl addressGatewayImpl(
            AddressRepository addressRepository
    ) {
        return new AddressGatewayImpl(addressRepository);
    }

}
