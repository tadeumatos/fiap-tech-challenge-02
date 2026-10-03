package br.com.fiap.tech.challenge._2.application.usecase.address.impl;


import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge._2.application.usecase.address.CreateAddressUseCase;
import br.com.fiap.tech.challenge._2.domain.Address;
import br.com.fiap.tech.challenge._2.exceptions.BusinessException;
import br.com.fiap.tech.challenge._2.presentation.controller.request.AddressRequest;
import java.util.UUID;

public class CreateAddressUseCaseImpl implements CreateAddressUseCase {
    private final AddressGateway addressGateway;

    public CreateAddressUseCaseImpl(AddressGateway addressGateway) {
        this.addressGateway = addressGateway;
    }

    @Override
    public Address execute(AddressRequest request) {

        if(validatePostalCode(request.postalCode()))
            throw new BusinessException("Already postal code with register, please choice other postal code");

        Address address = Address.create(UUID.randomUUID(),request.name(), request.neighborhood(), request.city(),request.state(),request.postalCode(),request.country());

        return addressGateway.save(address);
    }

    private boolean validatePostalCode(String postalCode)
    {
        return addressGateway.existPostalCode(postalCode);
    }
}
