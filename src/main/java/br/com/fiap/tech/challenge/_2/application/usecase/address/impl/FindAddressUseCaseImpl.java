package br.com.fiap.tech.challenge._2.application.usecase.address.impl;

import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge._2.application.usecase.address.FindAddressUseCase;
import br.com.fiap.tech.challenge._2.domain.Address;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;

import java.util.UUID;

public class FindAddressUseCaseImpl implements FindAddressUseCase {
    private final AddressGateway addressGateway;

    public FindAddressUseCaseImpl(AddressGateway addressGateway) {
        this.addressGateway = addressGateway;
    }

    @Override
    public Address execute(UUID id) {

        return addressGateway.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("The address not found"));
    }
}
