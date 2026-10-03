package br.com.fiap.tech.challenge._2.application.usecase.address;

import br.com.fiap.tech.challenge._2.domain.Address;

import java.util.List;


public interface GetAllAddressUseCase {

    List<Address> execute();
}
