package br.com.fiap.tech.challenge._2.application.usecase.address.impl;

import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.application.usecase.address.UpdateAddressUseCase;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.UpdateUserTypeUseCase;
import br.com.fiap.tech.challenge._2.domain.Address;
import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.exceptions.BusinessException;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.presentation.controller.request.AddressRequest;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserTypeRequest;

import java.util.UUID;

public class UpdateAddressUseCaseImpl implements UpdateAddressUseCase {
    private final AddressGateway addressGateway;

    public UpdateAddressUseCaseImpl(AddressGateway addressGateway) {
        this.addressGateway = addressGateway;
    }
    @Override
    public Address execute(UUID id, AddressRequest request) {

        // Verify that the address exists
        addressGateway.findById(id) .orElseThrow( () -> new ResourceNotFoundException( "The address not found" ) );

        if(validatePostalCode(id,request.postalCode()))
            throw new BusinessException("Already exist postal code with other register, please choice other postal code");

        Address address = Address.create(id,request.name(),request.neighborhood(),request.city(),request.state(),request.postalCode(),request.country());
        return addressGateway.update(id, address);
    }

    private boolean validatePostalCode(UUID id,String postalCode)
    {
        Address address= addressGateway.findByPostalCode(postalCode);

        if(address==null)
            return false;

        return !address.getId().equals(id) && address.getPostalCode().equals(postalCode);
    }
}
