package br.com.fiap.tech.challenge._2.presentation.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
   @NotBlank(message = "The name is required")
   @Size(min = 5, max=50, message="The size of field name must be between 5 and 50 caracter")
   String name,
   @NotBlank(message = "The email is required")
   @Size( max=100, message="The size of field email must be until 100 caracter")
   @Email(message = "Email invalid")
   String email,
   @NotBlank(message = "The user type id is required")
   String userTypeId
   ) {}



