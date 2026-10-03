package br.com.fiap.tech.challenge._2.application.usecase.address.impl;

import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge._2.application.usecase.address.GetAllAddressUseCase;
import br.com.fiap.tech.challenge._2.domain.Address;


import java.util.List;

public class GetAllAddressUseCaseImpl implements GetAllAddressUseCase {
    private final AddressGateway addressGateway;

    public GetAllAddressUseCaseImpl(AddressGateway addressGateway)
    {
        this.addressGateway = addressGateway;
    }

    @Override
    public List<Address> execute() {
        return addressGateway.getAll();
    }
}
