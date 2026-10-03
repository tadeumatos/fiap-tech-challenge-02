package br.com.fiap.tech.challenge._2.presentation.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(
        @NotBlank(message = "The name is required")
        @Size(min = 5, max=50, message="The size of field name must be between 5 and 50 caracter")
        String name,
        @NotBlank(message = "The neighborhood is required")
        @Size(min = 5, max=50, message="The size of field neighborhood must be between 5 and 50 caracter")
        String neighborhood,
        @NotBlank(message = "The city is required")
        @Size(min = 5, max=30, message="The size of field city must be between 5 and 30 caracter")
        String city,
        @NotBlank(message = "The state is required")
        @Size(min = 2, max=10, message="The size of field state must be between 2 and 10 caracter")
        String state,
        @NotBlank(message = "The postal code is required")
        @Size(min = 8, max=15, message="The size of field postal code must be between 8 and 15 caracter")
        String postalCode,
        @NotBlank(message = "The country is required")
        @Size(min = 4, max=30, message="The size of field country must be between 4 and 30 caracter")
        String country
        ) {
}





