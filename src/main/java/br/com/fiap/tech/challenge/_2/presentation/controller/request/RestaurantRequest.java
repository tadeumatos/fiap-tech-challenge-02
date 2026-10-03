package br.com.fiap.tech.challenge._2.presentation.controller.request;

import jakarta.validation.constraints.*;

import java.time.LocalTime;

public record RestaurantRequest(
   @NotBlank(message = "The name is required")
   @Size(min = 5, max=50, message="The size of field name must be between 5 and 50 caracter")
   String name,
   String description,
   @NotNull(message = "The address is required")
   String addressId,
   @NotBlank(message = "The address number is required")
   @Size(min = 1, max=10, message="The size of field address number must be between 1 and 10 caracter")
   String addressNumber,
   String addressComplement,
   @NotNull(message = "The user is required")
   String userId,
   @NotNull(message = "The food type id is required")
   String foodTypeId,
   @NotNull(message = "The start time is required")
   LocalTime startTime,
   @NotNull(message = "The end time is required")
   @Future
   LocalTime endTime

) {}



