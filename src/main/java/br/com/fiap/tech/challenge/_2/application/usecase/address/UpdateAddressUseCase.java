package br.com.fiap.tech.challenge._2.application.usecase.address;

import br.com.fiap.tech.challenge._2.domain.Address;
import br.com.fiap.tech.challenge._2.presentation.controller.request.AddressRequest;

import java.util.UUID;

public interface UpdateAddressUseCase {
    Address execute(UUID id, AddressRequest request);
}
