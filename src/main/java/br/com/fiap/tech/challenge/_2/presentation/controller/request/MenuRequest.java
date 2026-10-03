package br.com.fiap.tech.challenge._2.presentation.controller.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record MenuRequest(
   @NotBlank(message = "The name is required")
   @Size(min = 5, max=50, message="The size of field name must be between 5 and 50 caracter")
   String name,
   @NotBlank(message = "The description is required")
   @Size( max=100, message="The size of field description must be until 100 caracter")
   String description,
   @NotNull
   @Positive
   BigDecimal price,
   boolean onlyLocal,
   @NotBlank(message = "The name is required")
   String foodPhoto,
   @NotBlank(message = "The restaurant id is required")
   String restaurantId,
   boolean active
   ) {}



