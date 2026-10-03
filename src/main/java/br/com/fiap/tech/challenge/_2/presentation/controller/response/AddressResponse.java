package br.com.fiap.tech.challenge._2.presentation.controller.response;

import java.util.UUID;

public record AddressResponse(UUID id,String name,String neighborhood,String city,String state,String postalCode,String country) {
}


