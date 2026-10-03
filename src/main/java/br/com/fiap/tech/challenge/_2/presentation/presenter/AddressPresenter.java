package br.com.fiap.tech.challenge._2.presentation.presenter;

import br.com.fiap.tech.challenge._2.domain.Address;
import br.com.fiap.tech.challenge._2.presentation.controller.response.AddressResponse;
import java.util.ArrayList;
import java.util.List;

public class AddressPresenter {
    private AddressPresenter() {
    }

    public static AddressResponse toResponse(Address address) {

        return new AddressResponse(
           address.getId(),
           address.getName(),
           address.getNeighborhood(),
           address.getCity(),
           address.getState(),
           address.getPostalCode(),
           address.getCountry()
        );
    }

    public static List<AddressResponse> toResponseList(List<Address> list) {
        List<AddressResponse> responseList = new ArrayList<>();

        for (Address address :list) {

            responseList.add(
              new AddressResponse(
                  address.getId(),
                  address.getName(),
                  address.getNeighborhood(),
                  address.getCity(),
                  address.getState(),
                  address.getPostalCode(),
                  address.getCountry()
              )
            );
        }
        return responseList;
    }

}
