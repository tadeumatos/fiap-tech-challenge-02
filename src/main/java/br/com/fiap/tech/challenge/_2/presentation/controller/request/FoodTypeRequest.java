package br.com.fiap.tech.challenge._2.presentation.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FoodTypeRequest(
   @NotBlank(message = "The name is required")
   @Size(min = 5, max=50, message="The size of field name must be between 5 and 50 caracter")
   String name
) {}



